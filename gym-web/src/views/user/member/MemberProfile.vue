<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <h2>用户基本资料</h2>
      </div>
    </template>

    <router-view :key="route.fullPath"></router-view>
    <!-- 表单部分 -->
    <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
      <el-form-item label="用户ID" prop="userId">
        <el-input v-model="form.userId" disabled></el-input>
      </el-form-item>
      <el-form-item label="登录名称" prop="userName">
        <el-input v-model="form.userName" disabled></el-input>
      </el-form-item>
      <el-form-item label="姓名" prop="userRealName">
        <el-input v-model="form.userRealName"></el-input>
      </el-form-item>
      <el-form-item label="手机号" prop="userPhone">
        <el-input v-model="form.userPhone"></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-radio-group v-model="form.sex">
          <el-radio :label="0">男</el-radio>
          <el-radio :label="1">女</el-radio>
          <el-radio :label="2">保密</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="生日" prop="dateBirth">
        <el-date-picker
            v-model="form.dateBirth"
            type="date"
            placeholder="选择日期"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm">提交修改</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script setup>
import {ref} from 'vue'
import {useUserStore} from '@/stores'
import {userUpdateInfoService} from '@/apis/user'
import {ElMessage} from "element-plus";
import {useRoute} from 'vue-router'

const route = useRoute()

const formRef = ref()

// 是在使用仓库中数据的初始值 (无需响应式) 解构无问题
const {
  user: {
    avatar, dateBirth, userId, userName, userRealName, userPhone, sex
  },
  getUser
} = useUserStore()

//console.log('userRealName', userRealName)

const form = ref({
  avatar,
  dateBirth,
  userId,
  userName,
  userRealName,
  userPhone,
  sex
})

const rules = ref({
  userRealName: [
    {required: true, message: '请输入用户姓名', trigger: 'blur'},
    {
      pattern: /^[\u4E00-\u9FA5]{2,10}/,
      message: '昵称长度在2-10个非空字符',
      trigger: 'blur'
    }
  ],
  userPhone: [
    {required: true, message: '请输入用户手机号', trigger: 'blur'},
    {
      pattern: /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/,
      message: '请输入正确的手机号格式',
      trigger: 'blur'
    }
  ],
  dateBirth: [
    {required: true, message: '请选择出生日期', trigger: 'blur'},
    {
      required: true,
      trigger: 'blur'
    }
  ],
  userSex: [
    {required: true, message: '请选择性别', trigger: 'blur'},
    {
      required: true,
      trigger: 'blur'
    }
  ]
})

const submitForm = async () => {
  // 等待校验结果
  await formRef.value.validate();
  // 提交修改
  await userUpdateInfoService(form.value);
  // 通知 user 模块，进行数据的更新
  await getUser();
  // 提示用户
  ElMessage.success('修改成功');
}
</script>


<style scoped lang="stylus">

</style>
