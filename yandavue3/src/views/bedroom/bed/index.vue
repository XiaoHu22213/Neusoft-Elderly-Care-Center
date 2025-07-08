<template>
  <div class="bed-management-container">
    <!-- 顶部操作栏 -->
    <div class="operation-bar">
      <el-button type="primary" :icon="CirclePlus" @click="add" class="add-btn">
        添加床位
      </el-button>
      
      <div class="search-container">
        <el-input
          v-model="params.name"
          placeholder="搜索床位编号"
          class="search-input"
          clearable
          @clear="search"
        >
          <template #prefix>
            <el-icon><Search /></el-icon>
          </template>
        </el-input>
        <el-button type="primary" :icon="Search" @click="search" class="search-btn">
          搜索
        </el-button>
      </div>
    </div>

    <!-- 按字母分组的床位视图 -->
    <div class="bed-groups-container">
      <div v-for="(group, letter) in groupedBedData" :key="letter" class="bed-group">
        <div class="group-header">
          <span class="group-letter">{{ letter }}</span>
          <span class="group-count">({{ group.length }}个床位)</span>
        </div>
        <div class="bed-icons-group">
          <div 
            v-for="bed in group" 
            :key="bed.id"
            :class="['bed-icon', `status-${bed.status}`]"
            @click="showBedInfo(bed)"
          >
            <div class="bed-icon-content">
              <el-icon class="bed-icon-img" :size="40">
                <component :is="getBedIcon(bed.status)" />
              </el-icon>
              <span class="bed-number">#{{ bed.bedid }}</span>
              <span v-if="bed.peoplename" class="bed-occupant">
                {{ bed.peoplename }}
              </span>
              <span v-else class="bed-occupant empty">空闲</span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 床位信息弹窗 -->
    <el-dialog 
      v-model="bedInfoVisible" 
      :title="`床位 #${currentBed?.bedid}`" 
      width="500px"
      :close-on-click-modal="false"
    >
      <div v-if="currentBed" class="bed-info-content">
        <div class="info-row">
          <span class="info-label">状态：</span>
          <el-tag :type="getStatusTagType(currentBed.status)" effect="light">
            {{ currentBed.status }}
          </el-tag>
        </div>
        
        <div class="info-row" v-if="currentBed.peoplename">
          <span class="info-label">入住人：</span>
          <span class="info-value">{{ currentBed.peoplename }}</span>
        </div>
        
        <div class="info-actions">
          <template v-if="currentBed.status === '离席'">
            <el-button type="success" @click="addstatus(currentBed.id)">
              归来
            </el-button>
            <el-button type="danger" @click="delstatus(currentBed.id)">
              清空
            </el-button>
          </template>
          
          <el-button 
            v-if="currentBed.status === '占用'" 
            type="warning" 
            @click="del(currentBed.bedid, currentBed.peoplename)"
          >
            离席
          </el-button>
          
          <el-button 
            v-if="!currentBed.peopleid" 
            type="primary" 
            @click="update(currentBed.id)"
          >
            添加客户
          </el-button>
          
          <el-button 
            v-if="!currentBed.peopleid" 
            type="danger" 
            @click="beddel(currentBed.id)"
          >
            删除床位
          </el-button>
        </div>
      </div>
    </el-dialog>

    <!-- 分页 -->
    <div class="pagination-container">
      <el-pagination
        v-model:current-page="params.pageNo"
        :page-size="params.pageSize"
        :total="tableData.total"
        layout="total, prev, pager, next, jumper"
        @current-change="getTableData"
        background
      />
    </div>
    
    <!-- 添加/编辑床位对话框 -->
    <el-dialog 
      v-model="dialog.show" 
      :title="dialog.title" 
      width="500px"
      :close-on-click-modal="false"
    >
      <Add 
        v-if="dialog.show"
        v-model:show="dialog.show"  
        @getTableData="getTableData"
        :id="dialog.id"
      />
    </el-dialog>
    
    <!-- 离席记录对话框 -->
    <el-dialog 
      v-model="dia.show" 
      :title="dia.title" 
      width="600px"
      :close-on-click-modal="false"
    >
      <Outin
        v-if="dia.show"
        v-model:show="dia.show"  
        @getTableData="getTableData"
        :bedid="dia.bedid"
        :peoplename="dia.peoplename"
      />
    </el-dialog>
  </div>
</template>

<script setup>
import { User, House, CirclePlus, Search, OfficeBuilding, HomeFilled } from '@element-plus/icons-vue';
import { reactive, ref, computed } from 'vue';
import { request, get, post } from '@/axios';
import Add from './add.vue';
import { ElMessageBox, ElMessage } from 'element-plus';
import Outin from './outin.vue';

// 对话框状态
const dialog = reactive({
  show: false,
  title: '',
  id: null
});

const dia = reactive({
  show: false,
  title: '',
  bedid: null,
  peoplename: null
});

// 表格数据
const tableData = reactive({
  records: [],
  pages: 0,
  total: 0
});

// 查询参数
const params = reactive({
  pageNo: 1,
  pageSize: 50, // 增大每页显示数量
  name: null
});

const loading = ref(false);
const bedInfoVisible = ref(false);
const currentBed = ref(null);

// 计算属性：按首字母分组的床位数据
const groupedBedData = computed(() => {
  const groups = {};
  
  // 先获取排序后的床位数据
  const sortedBeds = [...tableData.records].sort((a, b) => {
    const extractParts = (bedid) => {
      const match = String(bedid).match(/^([A-Za-z]*)(\d*)$/);
      return {
        letter: match[1] ? match[1].toUpperCase() : '',
        number: match[2] ? parseInt(match[2], 10) : 0
      };
    };
    
    const aParts = extractParts(a.bedid);
    const bParts = extractParts(b.bedid);
    
    if (aParts.letter < bParts.letter) return -1;
    if (aParts.letter > bParts.letter) return 1;
    return aParts.number - bParts.number;
  });

  // 按首字母分组
  sortedBeds.forEach(bed => {
    const letter = String(bed.bedid).match(/^[A-Za-z]/)?.[0]?.toUpperCase() || '其他';
    if (!groups[letter]) {
      groups[letter] = [];
    }
    groups[letter].push(bed);
  });

  return groups;
});

// 获取床位图标
const getBedIcon = (status) => {
  const icons = {
    '占用': HomeFilled,
    '空闲': HomeFilled,
    '离席': OfficeBuilding
  };
  return icons[status] || HomeFilled;
};

// 获取状态对应的标签类型
const getStatusTagType = (status) => {
  const map = {
    '占用': 'primary',
    '空闲': 'success',
    '离席': 'danger'
  };
  return map[status] || '';
};

// 显示床位信息
const showBedInfo = (bed) => {
  currentBed.value = bed;
  bedInfoVisible.value = true;
};

// 获取床位数据
const getTableData = async () => {
  try {
    loading.value = true;
    await get('/bedroom/list', params, content => {
      tableData.records = content.records;
      tableData.pages = content.pages;
      tableData.total = content.total;
    });
  } finally {
    loading.value = false;
  }
};

// 添加床位
const add = () => {
  dialog.show = true;
  dialog.title = '添加床位';
  dialog.id = null;
  params.pageNo = 1; // 添加后回到第一页
};

// 编辑床位
const update = (id) => {
  dialog.show = true;
  dialog.title = '编辑床位';
  dialog.id = id;
  bedInfoVisible.value = false;
};

// 搜索
const search = () => {
  params.pageNo = 1;
  getTableData();
};

// 离席操作
const del = (bedid, peoplename) => {
  dia.show = true;
  dia.title = '离席记录';
  dia.bedid = bedid;
  dia.peoplename = peoplename;
  bedInfoVisible.value = false;
};

// 归来操作
const addstatus = async (id) => {
  try {
    await post("/bedroom/addstatus", { id });
    ElMessage.success('操作成功');
    getTableData();
    bedInfoVisible.value = false;
  } catch (error) {
    ElMessage.error('操作失败');
  }
};

// 清空床位
const delstatus = async (id) => {
  try {
    await post("/bedroom/delstatus", { id });
    ElMessage.success('操作成功');
    getTableData();
    bedInfoVisible.value = false;
  } catch (error) {
    ElMessage.error('操作失败');
  }
};

// 删除床位
const beddel = (id) => {
  ElMessageBox.confirm('确定要删除该床位吗？此操作不可恢复！', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await request(`/bedroom/deletebed/${id}`, 'delete', {});
      ElMessage.success('删除成功');
      getTableData();
      bedInfoVisible.value = false;
    } catch (error) {
      ElMessage.error('删除失败');
    }
  }).catch(() => {
    // 用户取消操作
  });
};

// 初始化加载数据
getTableData();
</script>

<style scoped lang="scss">
.bed-management-container {
  padding: 20px;
  background-color: #f5f7fa;
  min-height: calc(100vh - 60px);
}

.operation-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding: 15px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
  
  .add-btn {
    height: 36px;
    padding: 0 20px;
  }
}

.search-container {
  display: flex;
  align-items: center;
  
  .search-input {
    width: 300px;
    margin-right: 10px;
    
    :deep(.el-input__inner) {
      height: 36px;
    }
  }
  
  .search-btn {
    height: 36px;
    padding: 0 15px;
  }
}

/* 新增分组样式 */
.bed-groups-container {
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin-bottom: 20px;
}

.bed-group {
  background-color: #fff;
  border-radius: 10px;
  padding: 15px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.group-header {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
  padding-bottom: 10px;
  border-bottom: 1px solid #eee;
  
  .group-letter {
    font-size: 18px;
    font-weight: bold;
    color: #409eff;
    margin-right: 10px;
  }
  
  .group-count {
    font-size: 14px;
    color: #909399;
  }
}

.bed-icons-group {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(120px, 1fr));
  gap: 15px;
}

.bed-icon {
  border-radius: 8px;
  padding: 15px;
  cursor: pointer;
  transition: all 0.3s;
  text-align: center;
  
  &:hover {
    transform: translateY(-5px);
    box-shadow: 0 5px 15px rgba(0, 0, 0, 0.15);
  }
  
  &.status-占用 {
    border-top: 4px solid #409eff;
    
    .bed-icon-img {
      color: #409eff;
    }
  }
  
  &.status-空闲 {
    border-top: 4px solid #67c23a;
    
    .bed-icon-img {
      color: #67c23a;
    }
  }
  
  &.status-离席 {
    border-top: 4px solid #f56c6c;
    
    .bed-icon-img {
      color: #f56c6c;
    }
  }
}

.bed-icon-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 5px;
}

.bed-number {
  font-weight: bold;
  color: #606266;
  font-size: 14px;
}

.bed-occupant {
  font-size: 12px;
  color: #666;
  text-align: center;
  max-width: 100px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  
  &.empty {
    color: #67c23a;
    font-style: italic;
  }
}

.bed-info-content {
  padding: 10px;
  
  .info-row {
    display: flex;
    align-items: center;
    margin-bottom: 15px;
    
    .info-label {
      width: 80px;
      font-weight: bold;
      color: #606266;
    }
    
    .info-value {
      flex: 1;
      color: #303133;
    }
  }
  
  .info-actions {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
    margin-top: 20px;
    justify-content: center;
  }
}

.pagination-container {
  display: flex;
  justify-content: center;
  padding: 15px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
}
</style>