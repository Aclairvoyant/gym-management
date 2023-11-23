<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <h2>会员列表</h2>
        <div class="extra">
          <el-input v-model="searchName" placeholder="请输入会员姓名" style="width: 200px;" :prefix-icon="Search" class="search-input"></el-input>
          <el-button type="primary" @click="searchMember">搜索</el-button>
          <el-button type="primary" @click="addMember">添加会员</el-button>
        </div>
      </div>
    </template>

    <!-- 表单 -->
    <el-table v-loading="loading.value" :data="members" style="width: 100%">
      <el-table-column prop="memberCardId" label="会员卡号" width="150"></el-table-column>
      <el-table-column prop="userId" label="用户ID" width="150"></el-table-column>
      <el-table-column prop="userRealName" label="会员姓名" width="180"></el-table-column>
      <el-table-column prop="sex" label="性别" width="150">
        <template #default="{ row }">
          {{ genderText(row.sex) }}
        </template>
      </el-table-column>
      <el-table-column prop="userPhone" label="手机号" width="180"></el-table-column>

      <el-table-column prop="memberCardStatus" label="会员状态" width="180">
        <template #default="{ row }">
          {{ statusText(row.memberCardStatus) }}
        </template>
      </el-table-column>
      <el-table-column prop="memberFee" label="余额" width="150"></el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="180">
        <template #default="{ row }">
          {{ formatDate(row.createTime) }}
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
    </div>


  </el-card>


  <!-- 编辑会员信息对话框 -->
  <el-dialog v-model="editDialogVisible" title="编辑会员信息">
    <el-form :model="editFormData">
      <el-form-item label="会员卡号" :label-width="formLabelWidth">
        <el-input v-model="editFormData.memberCardId" disabled></el-input>
      </el-form-item>
      <el-form-item label="会员姓名" :label-width="formLabelWidth">
        <el-input v-model="editFormData.userRealName" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="性别" :label-width="formLabelWidth">
        <el-radio-group v-model="editFormData.sex">
          <el-radio :label="0">男</el-radio>
          <el-radio :label="1">女</el-radio>
          <el-radio :label="2">保密</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="手机号" :label-width="formLabelWidth">
        <el-input v-model="editFormData.userPhone" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="会员状态" :label-width="formLabelWidth">
        <el-radio-group v-model="editFormData.memberCardStatus">
          <el-radio :label="0">未激活</el-radio>
          <el-radio :label="1">激活</el-radio>
          <el-radio :label="2">已过期</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="余额" :label-width="formLabelWidth">
        <el-input v-model="editFormData.memberFee" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="editDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="updateMemberInfo">确认</el-button>
      </span>
    </template>
  </el-dialog>


  <!-- 添加会员信息对话框 -->
  <el-dialog v-model="addDialogVisible" title="添加会员信息">
    <el-form :model="addFormData">
      <el-form-item label="会员卡号" :label-width="formLabelWidth">
        <el-input disabled placeholder="卡号由系统生成"></el-input>
      </el-form-item>
      <el-form-item label="会员姓名" :label-width="formLabelWidth">
        <el-input v-model="addFormData.userRealName" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="性别" :label-width="formLabelWidth">
        <el-radio-group v-model="addFormData.sex">
          <el-radio :label="0">男</el-radio>
          <el-radio :label="1">女</el-radio>
          <el-radio :label="2">保密</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="手机号" :label-width="formLabelWidth">
        <el-input v-model="addFormData.userPhone" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="会员状态" :label-width="formLabelWidth">
        <el-radio-group v-model="addFormData.memberCardStatus">
          <el-radio :label="0">未激活</el-radio>
          <el-radio :label="1">激活</el-radio>
          <el-radio :label="2">已过期</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="余额" :label-width="formLabelWidth">
        <el-input v-model="addFormData.memberFee" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="addDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="addNewMember">确认</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import {
  addMemberService,
  deleteMemberService,
  editMemberService,
  getMemberListService,
  searchMemberService
} from '@/apis/member';
import {ElMessage, ElMessageBox} from "element-plus";
import {ref, onMounted} from "vue";
import {Delete, Edit, Search} from "@element-plus/icons-vue";


const members = ref([]); // 定义 members

// const fetchMembers = async () => {
//   try {
//     loading.value = true;
//     const response = await getMemberListService();
//     members.value = response.data.data; // 使用响应数据更新 members
//     //members.value = []
//     loading.value = false;
//   } catch (error) {
//     console.error('获取会员列表失败:', error);
//   }
// };

// 获取会员列表，并支持分页
const fetchMembers = async () => {
  try {
    loading.value = true;
    const pageNum = pagination.value.currentPage;
    const pageSize = pagination.value.pageSize;

    //console.log(params)
    const response = await getMemberListService({pageNum, pageSize});
    members.value = response.data.data.items;
    pagination.value.total = response.data.data.total;
    loading.value = false;
  } catch (error) {
    console.error('获取会员列表失败:', error);
  }
};

onMounted(fetchMembers);

// 分页数据
const pagination = ref({
  currentPage: 1,
  pageSize: 10,
  total: 0
});

// 处理每页显示条数变化
const handleSizeChange = (newSize) => {
  pagination.value.pageSize = newSize;
  fetchMembers();
};

// 处理当前页变化
const handleCurrentChange = (newPage) => {
  pagination.value.currentPage = newPage;
  fetchMembers();
};

const genderText = (genderCode) => {
  const genders = {
    '0': '男',
    '1': '女',
    '2': '保密'
  };
  return genders[genderCode] || '未知'; // 如果代码不是0、1或2，则返回'未知'
};

const statusText = (statusCode) => {
  const status = {
    '0': '未激活',
    '1': '已激活',
    '2': '已过期'
  };
  return status[statusCode];
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

const formLabelWidth = '100px';
const formLabelHeight = '10px';
const editDialogVisible = ref(false); // 控制编辑对话框的显示
const editFormData = ref({}); // 存储编辑表单数据
const addDialogVisible = ref(false); // 控制添加对话框的显示
const addFormData = ref({}); // 存储添加表单数据

// 打开编辑对话框
const handleEdit = (members) => {
  //console.log('编辑会员信息', members);
  editFormData.value = {...members}; // 深拷贝会员信息到表单数据
  console.log(editFormData.value)
  editDialogVisible.value = true; // 显示对话框
  console.log(editDialogVisible.value)
};

// 打开添加对话框
const addMember = () => {
  console.log('添加会员');
  addDialogVisible.value = true; // 显示对话框
};


// 更新会员信息的逻辑
const updateMemberInfo = async () => {
  console.log('更新会员信息', editFormData.value);
  try {
    await editMemberService(editFormData.value);

    ElMessage.success('会员信息更新成功');
  } catch (error) {
    console.error('更新会员信息失败:', error);
    ElMessage.error('更新失败');
  }

  editDialogVisible.value = false;
  await fetchMembers(); // 重新加载会员列表
};

// 删除会员
const handleDelete = (members) => {
  console.log('删除会员操作', members.userRealName)
  ElMessageBox.confirm(`确定删除会员 ${members.userRealName} 吗?`, '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
      .then(async () => {
        // 用户确认删除
        //console.log('删除会员', members);
        // 调用 API 删除会员...
        try {
          //console.log('删除会员', members.memberCardId)
          await deleteMemberService({ memberCardId: members.memberCardId });
        } catch (error) {
          console.error('删除会员失败:', error);
          ElMessage.error('删除失败');
        }
        ElMessage.success('会员删除成功');
      })
      .catch(() => {
        // 用户取消删除
        ElMessage.info('取消删除操作');
      })
      .finally(async () => {
        // 不管用户是否取消，都会执行这里的代码
        await fetchMembers(); // 重新加载会员列表
      });
};


const loading = ref(false); // 控制加载状态的显示

// 添加会员
const addNewMember = async () => {
  try {
    const memberData = {
      userRealName: addFormData.value.userRealName,
      memberCardStatus: addFormData.value.memberCardStatus,
      sex: addFormData.value.sex,
      userPhone: addFormData.value.userPhone,
      memberFee: addFormData.value.memberFee
    };
    console.log('添加会员信息', memberData);
    await addMemberService(memberData);
    ElMessage.success('会员添加成功');

    addFormData.value = {}; // 清空表单数据
    memberData.value = {};

    addDialogVisible.value = false;
    await fetchMembers(); // 重新加载会员列表
  } catch (error) {
    console.error('添加会员失败:', error);
    ElMessage.error('添加失败');
  }
};

// 搜索会员
const searchName = ref('');
const searchMember = async () => {
  try {
    const userRealName = searchName.value;
    const pageNum = pagination.value.currentPage;
    const pageSize = pagination.value.pageSize;
    const response = await searchMemberService({ pageNum, pageSize, userRealName });
    members.value = response.data.data.items; // 使用搜索结果更新会员列表
  } catch (error) {
    console.error('搜索会员失败:', error);
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
