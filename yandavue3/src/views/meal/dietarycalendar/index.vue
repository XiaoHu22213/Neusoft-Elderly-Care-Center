<template>
  <div class="container">
    <div class="header-section">
      <div class="btns">
        <el-button type="primary" plain :icon="CirclePlus" @click="add">添加菜品</el-button>
      </div>
      
      <div class="days">
        <el-radio-group v-model="params.days" size="large" @change="search">
          <el-radio-button v-for="day in daysOptions" 
                          :key="day.value" 
                          :label="day.label" 
                          :value="day.value"
                          :class="{ 'active-day': params.days === day.value }"/>
        </el-radio-group>
      </div>
    </div>
    
    <div class="meal-section breakfast" style="margin-top: 25px;">
      <div class="meal-header">
        <div class="meal-title">
          <span class="meal-icon">🍳</span>
          <h3>早餐</h3>
        </div>
        <div class="meal-time">07:00-09:00</div>
      </div>
      
      <div class="meal-cards">
        <div v-for="item in Breakfast" :key="item.id" class="meal-card">
          <div class="card-content">
            <div class="card-image">
              <el-image style="width: 100%; height: 140px;" fit="cover" :src="getPath(item.icon)" 
                       @error="handleImageError(item.icon)" class="meal-image">
                <template #error>
                  <div class="image-error">
                    <el-icon><Picture /></el-icon>
                    <span>图片加载失败</span>
                  </div>
                </template>
              </el-image>
            </div>
            <div class="card-info">
              <h4 class="meal-name">{{ item.mealname }}</h4>
              <div class="info-row">
                <span class="info-label">类型:</span>
                <span class="info-value">{{ item.type }}</span>
              </div>
              <div class="info-row">
                <span class="info-label">价格:</span>
                <span class="info-value price">¥{{ item.price }}</span>
              </div>
              <div class="info-row">
                <span class="info-label">清真:</span>
                <span class="info-value" :class="{ 'halal': item.qingzhen === 1 }">
                  {{ item.qingzhen === 1 ? '是' : '否' }}
                </span>
              </div>
            </div>
          </div>
          <div class="card-actions">
            <template v-if="item.status">
              <el-button type="primary" plain size="small" @click="update(item.id)" class="action-btn">修改</el-button>
              <el-button type="danger" plain size="small" @click="del(item.id, 0)" class="action-btn">删除</el-button>
            </template>
            <el-button v-else type="warning" plain size="small" @click="del(item.id, 1)" class="action-btn">启用</el-button>
          </div>
        </div>
      </div>
    </div>
    
    <div class="meal-section lunch" style="margin-top: 30px;">
      <div class="meal-header">
        <div class="meal-title">
          <span class="meal-icon">🍲</span>
          <h3>午餐</h3>
        </div>
        <div class="meal-time">11:00-13:00</div>
      </div>
      
      <div class="meal-cards">
        <div v-for="item in Lunch" :key="item.id" class="meal-card">
          <div class="card-content">
            <div class="card-image">
              <el-image style="width: 100%; height: 140px;" fit="cover" :src="getPath(item.icon)" class="meal-image">
                <template #error>
                  <div class="image-error">
                    <el-icon><Picture /></el-icon>
                    <span>图片加载失败</span>
                  </div>
                </template>
              </el-image>
            </div>
            <div class="card-info">
              <h4 class="meal-name">{{ item.mealname }}</h4>
              <div class="info-row">
                <span class="info-label">类型:</span>
                <span class="info-value">{{ item.type }}</span>
              </div>
              <div class="info-row">
                <span class="info-label">价格:</span>
                <span class="info-value price">¥{{ item.price }}</span>
              </div>
              <div class="info-row">
                <span class="info-label">清真:</span>
                <span class="info-value" :class="{ 'halal': item.qingzhen === 1 }">
                  {{ item.qingzhen === 1 ? '是' : '否' }}
                </span>
              </div>
            </div>
          </div>
          <div class="card-actions">
            <template v-if="item.status">
              <el-button type="primary" plain size="small" @click="update(item.id)" class="action-btn">修改</el-button>
              <el-button type="danger" plain size="small" @click="del(item.id, 0)" class="action-btn">删除</el-button>
            </template>
            <el-button v-else type="warning" plain size="small" @click="del(item.id, 1)" class="action-btn">启用</el-button>
          </div>
        </div>
      </div>
    </div>
    
    <div class="meal-section dinner" style="margin-top: 30px;">
      <div class="meal-header">
        <div class="meal-title">
          <span class="meal-icon">🍛</span>
          <h3>晚餐</h3>
        </div>
        <div class="meal-time">17:00-19:00</div>
      </div>
      
      <div class="meal-cards">
        <div v-for="item in Dinner" :key="item.id" class="meal-card">
          <div class="card-content">
            <div class="card-image">
              <el-image style="width: 100%; height: 140px;" fit="cover" :src="getPath(item.icon)" class="meal-image">
                <template #error>
                  <div class="image-error">
                    <el-icon><Picture /></el-icon>
                    <span>图片加载失败</span>
                  </div>
                </template>
              </el-image>
            </div>
            <div class="card-info">
              <h4 class="meal-name">{{ item.mealname }}</h4>
              <div class="info-row">
                <span class="info-label">类型:</span>
                <span class="info-value">{{ item.type }}</span>
              </div>
              <div class="info-row">
                <span class="info-label">价格:</span>
                <span class="info-value price">¥{{ item.price }}</span>
              </div>
              <div class="info-row">
                <span class="info-label">清真:</span>
                <span class="info-value" :class="{ 'halal': item.qingzhen === 1 }">
                  {{ item.qingzhen === 1 ? '是' : '否' }}
                </span>
              </div>
            </div>
          </div>
          <div class="card-actions">
            <template v-if="item.status">
              <el-button type="primary" plain size="small" @click="update(item.id)" class="action-btn">修改</el-button>
              <el-button type="danger" plain size="small" @click="del(item.id, 0)" class="action-btn">删除</el-button>
            </template>
            <el-button v-else type="warning" plain size="small" @click="del(item.id, 1)" class="action-btn">启用</el-button>
          </div>
        </div>
      </div>
    </div>

    <el-pagination
      style="margin-top: 30px;"
      background
      v-model:current-page="params.pageNo"
      :page-count="tableData.pages"
      :total="tableData.total"
      layout="prev, pager, next"
      @current-change="getTableData" />
    
    <el-dialog
      v-model="dialog.show"
      :title="dialog.title"
      width="500px"
      :close-on-click-modal="false">
      <Add
        v-if="dialog.show"
        @getTableData="getTableData"
        v-model:show="dialog.show"
        :id="dialog.id"/>
    </el-dialog>
  </div>
</template>

<script setup>
import { CirclePlus, Picture } from '@element-plus/icons-vue'
import { getPath } from '@/util'
import { get, post } from '@/axios'
import { ref, reactive, onMounted, computed } from 'vue'
import Add from './add'
import url from './util'
import { ElMessageBox } from 'element-plus'

const daysOptions = [
  { label: '周一', value: 'Monday' },
  { label: '周二', value: 'Tuesday' },
  { label: '周三', value: 'Wednesday' },
  { label: '周四', value: 'Thursday' },
  { label: '周五', value: 'Friday' },
  { label: '周六', value: 'Saturday' },
  { label: '周日', value: 'Sunday' }
]

const dialog = reactive({
  show: false,
  title: '',
  id: null
})

const tableData = reactive({
  records: [],
  pages: 0,
  total: 0
})

const params = reactive({
  pageNo: 1,
  pageSize: 10,
  days: '',
  mealtime: '早餐'
})

const mealData = ref([])
getmealData()

function getmealData() {
  get('/dietarycalendar/type', null, content => {
    mealData.value = content
  })
}

const dayOfWeekMapping = {
  0: 'Sunday',
  1: 'Monday',
  2: 'Tuesday',
  3: 'Wednesday',
  4: 'Thursday',
  5: 'Friday',
  6: 'Saturday'
}

onMounted(() => {
  const today = new Date()
  const dayOfWeek = today.getDay()
  params.days = dayOfWeekMapping[dayOfWeek]
})

// 计算属性
const Breakfast = computed(() => {
  return mealData.value.filter(item => 
    item.days === params.days && 
    item.mealtime === '早餐' && 
    item.status === true
  )
})

const Lunch = computed(() => {
  return mealData.value.filter(item => 
    item.days === params.days && 
    item.mealtime === '午餐'
  )
})

const Dinner = computed(() => {
  return mealData.value.filter(item => 
    item.days === params.days && 
    item.mealtime === '晚餐'
  )
})

getTableData()
function getTableData() {
  get(url.list, params, content => {
    const recordsArray = Array.prototype.slice.call(content.records)
    const sortedData = recordsArray.sort((a, b) => {
      const breakfastOrder = ['早餐', '午餐', '晚餐']
      const aIndex = breakfastOrder.indexOf(a.mealtime)
      const bIndex = breakfastOrder.indexOf(b.mealtime)
      if (aIndex < bIndex) return -1
      if (aIndex > bIndex) return 1
      return 0
    })
    
    tableData.records = sortedData
    tableData.pages = content.pages
    tableData.total = content.total
  })
}

function add() {
  dialog.title = '添加菜品'
  dialog.id = null
  dialog.show = true
}

function update(id) {
  dialog.title = '修改菜品'
  dialog.id = id
  dialog.show = true
}

function del(id, status) {
  const text = status ? '确定要启用该菜单吗?' : '确定要禁用该菜单吗'
  ElMessageBox.confirm(text, '警告', {
    type: 'warning'
  }).then(() => {
    post(url.del, { id, status }, content => {
      getTableData()
    })
  }).catch(() => {})
}

function search() {
  getTableData()
}

function handleImageError(iconPath) {
  console.error('图片加载失败:', getPath(iconPath))
}
</script>

<style scoped lang="scss">
.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.header-section {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding: 15px 20px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.days {
  .el-radio-group {
    display: flex;
    gap: 5px;
  }
  
  .el-radio-button {
    --el-radio-button-checked-bg-color: #409eff;
    --el-radio-button-checked-text-color: #fff;
    --el-radio-button-checked-border-color: #409eff;
    
    &.active-day {
      background-color: #409eff;
      color: #fff;
    }
  }
}

.meal-section {
  background: #fff;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  transition: all 0.3s ease;
  
  &:hover {
    box-shadow: 0 6px 16px rgba(0, 0, 0, 0.12);
  }
  
  &.breakfast {
    border-top: 4px solid #FFA500;
  }
  
  &.lunch {
    border-top: 4px solid #FF6347;
  }
  
  &.dinner {
    border-top: 4px solid #4682B4;
  }
}

.meal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 1px solid #eee;
  
  .meal-title {
    display: flex;
    align-items: center;
    gap: 10px;
    
    h3 {
      margin: 0;
      font-size: 18px;
      color: #333;
    }
    
    .meal-icon {
      font-size: 24px;
    }
  }
  
  .meal-time {
    color: #666;
    font-size: 14px;
  }
}

.meal-cards {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  gap: 20px;
}

.meal-card {
  background: #fff;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  display: flex;
  flex-direction: column;
  
  &:hover {
    transform: translateY(-5px);
    box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
  }
}

.card-content {
  padding: 15px;
}

.card-image {
  border-radius: 6px;
  overflow: hidden;
  margin-bottom: 12px;
  height: 140px;
  background: #f5f5f5;
  display: flex;
  align-items: center;
  justify-content: center;
  
  .meal-image {
    border-radius: 6px;
  }
  
  .image-error {
    display: flex;
    flex-direction: column;
    align-items: center;
    color: #999;
    
    .el-icon {
      font-size: 40px;
      margin-bottom: 5px;
    }
  }
}

.card-info {
  .meal-name {
    margin: 0 0 10px 0;
    font-size: 16px;
    color: #333;
    font-weight: 600;
  }
  
  .info-row {
    display: flex;
    margin-bottom: 6px;
    font-size: 13px;
    
    .info-label {
      color: #666;
      width: 50px;
      flex-shrink: 0;
    }
    
    .info-value {
      color: #333;
      
      &.price {
        color: #FF6347;
        font-weight: bold;
      }
      
      &.halal {
        color: #1E90FF;
        font-weight: bold;
      }
    }
  }
}

.card-actions {
  padding: 10px 15px;
  border-top: 1px solid #eee;
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  
  .action-btn {
    padding: 7px 12px;
    font-size: 12px;
  }
}

.el-pagination {
  display: flex;
  justify-content: center;
  margin-top: 30px;
}

@media (max-width: 768px) {
  .header-section {
    flex-direction: column;
    gap: 15px;
    align-items: flex-start;
  }
  
  .days .el-radio-group {
    flex-wrap: wrap;
  }
  
  .meal-cards {
    grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  }
}
</style>