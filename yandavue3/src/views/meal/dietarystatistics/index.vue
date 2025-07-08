<template>
  <div class="dietary-stats-container">
    <!-- 顶部操作栏 -->
    <div class="operation-bar">
      <div class="day-selector">
        <el-select 
          v-model="params.days" 
          clearable 
          placeholder="请选择日期" 
          style="width: 240px" 
          @change="search"
          class="day-select"
        >
          <el-option 
            v-for="item in dayOptions" 
            :key="item.value" 
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </div>
    </div>
    
    <!-- 膳食统计卡片 -->
    <div class="stats-cards">
      <div v-for="mealType in mealTypes" :key="mealType.type" class="meal-type-card">
        <div class="meal-type-header">
          <h3>{{ mealType.label }}</h3>
          <el-tag type="info" size="small">{{ mealType.time }}</el-tag>
        </div>
        
        <el-table 
          :data="statsData.filter(item => item.mealtype === mealType.type)" 
          stripe 
          border
          class="stats-table"
        >
          <el-table-column 
            prop="mealname" 
            label="菜品名称" 
            align="center" 
            width="180"
          />
          <el-table-column 
            prop="count" 
            label="消耗数量" 
            align="center" 
            width="120"
          >
            <template #default="scope">
              <span class="count-badge">{{ scope.row.count }}</span>
            </template>
          </el-table-column>
          <el-table-column 
            prop="type" 
            label="菜品类型" 
            align="center" 
            width="120"
          >
            <template #default="scope">
              <el-tag :type="getTagType(scope.row.type)">{{ scope.row.type }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column 
            prop="qingzhen" 
            label="清真" 
            align="center" 
            width="100"
          >
            <template #default="scope">
              <el-tag v-if="scope.row.qingzhen === 1" type="success">是</el-tag>
              <el-tag v-else type="info">否</el-tag>
            </template>
          </el-table-column>
          <el-table-column 
            label="操作" 
            align="center" 
            width="120"
          >
            <template #default="scope">
              <el-button 
                type="primary" 
                size="small" 
                plain 
                @click="viewDetails(scope.row)"
              >
                详情
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    
    <!-- 分页 -->
    <el-pagination
      class="pagination"
      background
      v-model:current-page="params.pageNo"
      :page-size="params.pageSize"
      :total="tableData.total"
      layout="prev, pager, next, jumper, total"
      @current-change="getStatsData"
    />
    
    <!-- 详情弹窗 -->
    <el-dialog 
      v-model="detailDialog.show" 
      :title="detailDialog.title" 
      width="600px" 
      :close-on-click-modal="false"
    >
      <div v-if="detailDialog.show" class="detail-content">
        <div class="detail-header">
          <h3>{{ detailDialog.data.mealname }}</h3>
          <div class="detail-meta">
            <el-tag>{{ detailDialog.data.mealtype }}</el-tag>
            <el-tag :type="detailDialog.data.qingzhen === 1 ? 'success' : 'info'">
              {{ detailDialog.data.qingzhen === 1 ? '清真' : '非清真' }}
            </el-tag>
          </div>
        </div>
        
        <div class="detail-stats">
          <el-descriptions :column="2" border>
            <el-descriptions-item label="总消耗量">{{ detailDialog.data.count }}</el-descriptions-item>
            <el-descriptions-item label="消耗日期">{{ params.days }}</el-descriptions-item>
            <el-descriptions-item label="主要消耗时段">{{ getMealTime(detailDialog.data.mealtype) }}</el-descriptions-item>
            <el-descriptions-item label="平均每日消耗">{{ Math.round(detailDialog.data.count / 7) }}</el-descriptions-item>
          </el-descriptions>
        </div>
        
        <div class="detail-chart">
          <h4>最近7天消耗趋势</h4>
          <!-- 这里可以放置图表组件 -->
          <div class="chart-placeholder">
            <el-icon><TrendCharts /></el-icon>
            <span>消耗趋势图表</span>
          </div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { get, post } from '@/axios'
import { ElMessageBox, ElMessage } from 'element-plus'
import { CirclePlus, Search, TrendCharts } from '@element-plus/icons-vue'

// 对话框状态
const detailDialog = reactive({
  show: false,
  title: '',
  data: null
})

// 表格数据
const tableData = reactive({
  records: [],
  pages: 0,
  total: 0
})

// 统计数据
const statsData = ref([])

// 请求参数
const params = reactive({
  pageNo: 1,
  pageSize: 9,
  days: ''
})

// 日期选项
const dayOptions = [
  { value: 'Monday', label: '周一' },
  { value: 'Tuesday', label: '周二' },
  { value: 'Wednesday', label: '周三' },
  { value: 'Thursday', label: '周四' },
  { value: 'Friday', label: '周五' },
  { value: 'Saturday', label: '周六' },
  { value: 'Sunday', label: '周日' }
]

// 餐类型配置
const mealTypes = [
  { type: '早餐', label: '早餐统计', time: '07:00-09:00' },
  { type: '午餐', label: '午餐统计', time: '11:00-13:00' },
  { type: '晚餐', label: '晚餐统计', time: '17:00-19:00' },
  { type: '主食', label: '主食统计', time: '全天' },
  { type: '汤类', label: '汤类统计', time: '全天' },
  { type: '水果', label: '水果统计', time: '全天' }
]

// 标签类型映射
const tagTypeMap = {
  '主食': '',
  '汤类': 'info',
  '水果': 'success',
  '早餐': 'warning',
  '午餐': 'danger',
  '晚餐': 'primary'
}

// 获取统计数据
function getStatsData() {
  get('/dietarystats/list', params, content => {
    tableData.records = content.records
    tableData.pages = content.pages
    tableData.total = content.total
    
    // 处理统计数据
    processStatsData(content.records)
  })
}

// 处理统计数据
function processStatsData(records) {
  const stats = {}
  
  records.forEach(record => {
    if (!stats[record.mealname]) {
      stats[record.mealname] = {
        mealname: record.mealname,
        mealtype: record.type,
        qingzhen: record.qingzhen,
        count: 0
      }
    }
    stats[record.mealname].count++
  })
  
  statsData.value = Object.values(stats)
}

// 获取标签类型
function getTagType(type) {
  return tagTypeMap[type] || ''
}

// 获取餐时段
function getMealTime(type) {
  const meal = mealTypes.find(m => m.type === type)
  return meal ? meal.time : '全天'
}

// 查看详情
function viewDetails(row) {
  detailDialog.title = `菜品消耗详情 - ${row.mealname}`
  detailDialog.data = row
  detailDialog.show = true
}

// 搜索
function search() {
  params.pageNo = 1
  getStatsData()
}

// 初始化
onMounted(() => {
  const today = new Date()
  const dayOfWeek = today.getDay()
  params.days = dayOptions[dayOfWeek === 0 ? 6 : dayOfWeek - 1].value
  getStatsData()
})
</script>

<style scoped>
.dietary-stats-container {
  padding: 20px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.operation-bar {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.day-select {
  margin-right: 15px;
}

.stats-cards {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(500px, 1fr));
  gap: 20px;
  margin-top: 20px;
}

.meal-type-card {
  background: #fff;
  border-radius: 8px;
  padding: 15px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.meal-type-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
  padding-bottom: 10px;
  border-bottom: 1px solid #eee;
}

.stats-table {
  margin-top: 10px;
}

.count-badge {
  display: inline-block;
  padding: 2px 8px;
  background-color: #f0f7ff;
  color: #409eff;
  border-radius: 10px;
  font-weight: bold;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}

.detail-content {
  padding: 10px;
}

.detail-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.detail-meta {
  display: flex;
  gap: 10px;
}

.detail-stats {
  margin: 20px 0;
}

.detail-chart {
  margin-top: 30px;
  padding: 20px;
  background: #f5f7fa;
  border-radius: 8px;
}

.chart-placeholder {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 200px;
  color: #909399;
}

.chart-placeholder .el-icon {
  font-size: 50px;
  margin-bottom: 10px;
}

@media (max-width: 768px) {
  .stats-cards {
    grid-template-columns: 1fr;
  }
}
</style>