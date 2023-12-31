<template>


  <el-card class="page-container">
    <template #header>
      <div class="header">
        <h2>教练列表</h2>
        <div class="extra">
          <el-input v-model="searchName" placeholder="请输入教练姓名" style="width: 200px;" :prefix-icon="Search"
                    class="search-input"></el-input>
          <el-button type="primary" @click="searchCoach">搜索</el-button>
          <el-button type="primary" @click="addCoach">添加教练</el-button>
        </div>
      </div>
    </template>


    <router-view :key="route.fullPath"></router-view>
    <!-- 表单 -->
    <el-table v-loading="loading.value" :data="coachs" style="width: 100%">
      <el-table-column prop="coachId" label="教练ID" width="150"></el-table-column>
      <el-table-column prop="coachRealName" label="教练姓名" width="180"></el-table-column>
      <el-table-column prop="coachSex" label="性别" width="150">
        <template #default="{ row }">
          {{ genderText(row.coachSex) }}
        </template>
      </el-table-column>
      <el-table-column prop="coachPhone" label="手机号" width="180"></el-table-column>

      <el-table-column prop="coachRemark" label="专长及技能">
        <template #default="{ row }">
          <el-popover trigger="hover" placement="top">
            <p>{{ row.coachRemark }}</p>
            <template #reference>
              <div class="text-cell">{{ row.coachRemark }}</div>
            </template>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="180">
        <template #default="{ row }">
          {{ formatDate(row.createTime) }}
        </template>
      </el-table-column>
      <el-table-column prop="updateTime" label="更新时间" width="180">
        <template #default="{ row }">
          {{ formatDate(row.updateTime) }}
        </template>
      </el-table-column>


      <el-table-column label="操作" width="120" align="center" fixed="right">
        <template #default="{ row }">
          <el-button type="primary" size="mini" @click="handleEdit(row)" :icon="Edit" circle plain></el-button>
          <el-button type="danger" size="mini" @click="handleDelete(row)" :icon="Delete" circle plain></el-button>
        </template>
      </el-table-column>

      <template #empty>
        <el-empty description="没有数据"/>
      </template>
    </el-table>


    <!-- 分页 -->
    <div class="el-page">
      <el-config-provider :locale="language">
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="pagination.currentPage"
            :page-sizes="[10, 20, 30, 40, 50]"
            :page-size="pagination.pageSize"
            :total="pagination.total"
            :background="true"
            layout="jumper, total, sizes, prev, pager, next"
            style="margin-top: 20px;">
        </el-pagination>
      </el-config-provider>
    </div>

  </el-card>


  <!-- 编辑教练信息对话框 -->
  <el-dialog v-model="editDialogVisible" title="编辑教练信息">
    <el-form :model="editFormData">
      <el-form-item label="教练ID" :label-width="formLabelWidth">
        <el-input v-model="editFormData.coachId" disabled></el-input>
      </el-form-item>
      <el-form-item label="教练姓名" :label-width="formLabelWidth">
        <el-input v-model="editFormData.coachRealName" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="性别" :label-width="formLabelWidth">
        <el-radio-group v-model="editFormData.sex">
          <el-radio :label="0">男</el-radio>
          <el-radio :label="1">女</el-radio>
          <el-radio :label="2">保密</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="手机号" :label-width="formLabelWidth">
        <el-input v-model="editFormData.coachPhone" autocomplete="off"></el-input>
      </el-form-item>

      <el-form-item label="专长及技能" :label-width="formLabelWidth">
        <el-input v-model="editFormData.coachRemark" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
        <span class="dialog-footer">
          <el-button @click="editDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="updateCoachInfo">确认</el-button>
        </span>
    </template>
  </el-dialog>


  <!-- 添加教练信息对话框 -->
  <el-dialog v-model="addDialogVisible" title="添加教练信息">
    <el-form :model="addFormData">
      <el-form-item label="教练ID" :label-width="formLabelWidth">
        <el-input disabled placeholder="ID由系统自动生成"></el-input>
      </el-form-item>
      <el-form-item label="教练姓名" :label-width="formLabelWidth">
        <el-input v-model="addFormData.coachRealName" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="性别" :label-width="formLabelWidth">
        <el-radio-group v-model="addFormData.coachSex">
          <el-radio :label="0">男</el-radio>
          <el-radio :label="1">女</el-radio>
          <el-radio :label="2">保密</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="手机号" :label-width="formLabelWidth">
        <el-input v-model="addFormData.coachPhone" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="专长及技能" :label-width="formLabelWidth">
        <el-input v-model="addFormData.coachRemark" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
        <span class="dialog-footer">
          <el-button @click="addDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="addNewCoach">确认</el-button>
        </span>
    </template>
  </el-dialog>
</template>

<script setup>
import {ElMessage, ElMessageBox} from "element-plus";
import {ref, onMounted} from "vue";
import {Delete, Edit, Search} from "@element-plus/icons-vue";
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import {
  addCoachService,
  deleteCoachService,
  editCoachService,
  getCoachListService,
  searchCoachService
} from "@/apis/coach";
import {useRoute} from 'vue-router'

const route = useRoute()

const coachs = ref([]); // 定义 coachs
const language = ref(zhCn); // 定义语言
const loading = ref(false); // 控制加载状态的显示


// 分页数据
const pagination = ref({
  currentPage: 1,
  pageSize: 10,
  total: 0
});

// 处理每页显示条数变化
const handleSizeChange = (newSize) => {
  pagination.value.pageSize = newSize;
  fetchCoachs();
};

// 处理当前页变化
const handleCurrentChange = (newPage) => {
  pagination.value.currentPage = newPage;
  fetchCoachs();
};

const formatDate = (timestamp) => {
  return new Intl.DateTimeFormat('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: 'numeric',
    minute: 'numeric'
  }).format(new Date(timestamp));
};

// 获取教练列表，并支持分页
const fetchCoachs = async () => {
  try {
    loading.value = true;
    const pageNum = pagination.value.currentPage;
    const pageSize = pagination.value.pageSize;

    //console.log(params)
    const response = await getCoachListService({pageNum, pageSize});
    coachs.value = response.data.data.items;
    //console.log(coachs.value)
    pagination.value.total = response.data.data.total;
    //console.log(pagination.value.total)
    loading.value = false;
  } catch (error) {
    console.error('获取教练列表失败:', error);
  }
};

onMounted(fetchCoachs);


const genderText = (genderCode) => {
  const genders = {
    '0': '男',
    '1': '女',
    '2': '保密'
  };
  return genders[genderCode] || '未知'; // 如果代码不是0、1或2，则返回'未知'
};


const formLabelWidth = '100px';
const formLabelHeight = '10px';
const editDialogVisible = ref(false); // 控制编辑对话框的显示
const editFormData = ref({}); // 存储编辑表单数据
const addDialogVisible = ref(false); // 控制添加对话框的显示
const addFormData = ref({}); // 存储添加表单数据

// 打开编辑对话框
const handleEdit = (coachs) => {
  editFormData.value = {...coachs}; // 深拷贝会员信息到表单数据
  console.log(editFormData.value)
  editDialogVisible.value = true; // 显示对话框
  console.log(editDialogVisible.value)
};

// 打开添加对话框
const addCoach = () => {
  console.log('添加教练');
  addDialogVisible.value = true; // 显示对话框
};


// 更新教练信息的逻辑
const updateCoachInfo = async () => {
  console.log('更新教练信息', editFormData.value);
  try {
    if (editFormData.value.coachRealName === undefined || editFormData.value.coachRealName === '') {
      ElMessage.warning('请输入教练姓名');
      return;
    } else if (!/^[\u4E00-\u9FA5]{2,10}/.test(editFormData.value.coachRealName)) {
      ElMessage.warning('教练姓名格式不正确');
      return;
    }
    if (editFormData.value.coachPhone === undefined || editFormData.value.coachPhone === '') {
      ElMessage.warning('请输入手机号');
      return;
    } else if (!/^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/.test(editFormData.value.coachPhone)) {
      ElMessage.warning('手机号格式不正确');
      return;
    }
    if (editFormData.value.coachSex === undefined) {
      ElMessage.warning('请选择性别');
      return;
    }

    await editCoachService(editFormData.value);
    ElMessage.success('教练信息更新成功');
  } catch (error) {
    console.error('更新教练信息失败:', error);
    ElMessage.error('更新失败');
  }

  editDialogVisible.value = false;
  await fetchCoachs(); // 重新加载列表
};

// 删除教练
const handleDelete = (coachs) => {
  console.log('删除教练操作', coachs.coachRealName)
  ElMessageBox.confirm(`确定删除教练 ${coachs.coachRealName} 吗?`, '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
      .then(async () => {
        // 用户确认删除
        try {
          await deleteCoachService({coachId: coachs.coachId});
        } catch (error) {
          //console.error('删除教练失败:', error);
          ElMessage.error('删除失败');
        }
        ElMessage.success('教练删除成功');
      })
      .catch(() => {
        // 用户取消删除
        ElMessage.info('取消删除操作');
      })
      .finally(async () => {
        // 不管用户是否取消，都会执行这里的代码
        await fetchCoachs(); // 重新加载列表
      });
};


// 添加教练
const addNewCoach = async () => {
  try {
    const coachData = {
      coachRealName: addFormData.value.coachRealName,
      coachSex: addFormData.value.coachSex,
      coachPhone: addFormData.value.coachPhone,
      coachRemark: addFormData.value.coachRemark
    };
    if (coachData.coachRealName === undefined || coachData.coachRealName === '') {
      ElMessage.warning('请输入教练姓名');
      return;
    } else if (!/^[\u4E00-\u9FA5]{2,10}/.test(coachData.coachRealName)) {
      ElMessage.warning('教练姓名格式不正确');
      return;
    }
    if (coachData.coachPhone === undefined || coachData.coachPhone === '') {
      ElMessage.warning('请输入手机号');
      return;
    } else if (!/^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/.test(coachData.coachPhone)) {
      ElMessage.warning('手机号格式不正确');
      return;
    }
    if (coachData.coachSex === undefined) {
      ElMessage.warning('请选择性别');
      return;
    }
    console.log('添加教练信息', coachData);
    await addCoachService(coachData);
    ElMessage.success('教练添加成功');

    addFormData.value = {}; // 清空表单数据
    coachData.value = {};

    addDialogVisible.value = false;
    await fetchCoachs(); // 重新加载教练列表
  } catch (error) {
    console.error('添加教练失败:', error);
    ElMessage.error('添加失败');
  }
};

// 搜索教练
const searchName = ref('');
const searchCoach = async () => {
  try {
    const coachRealName = searchName.value;
    const pageNum = pagination.value.currentPage;
    const pageSize = pagination.value.pageSize;
    const response = await searchCoachService({pageNum, pageSize, coachRealName});
    coachs.value = response.data.data.items; // 使用搜索结果更新教练列表
  } catch (error) {
    console.error('搜索教练失败:', error);
  }
};


</script>

<style scoped>
.page-container {
  min-height: 100%;
  box-sizing: border-box;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.el-page {
  display: flex;
  justify-content: end;
  align-items: center;
}

.search-input {
  margin-right: 10px;
}

</style>
