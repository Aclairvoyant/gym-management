<template>
  <el-row>
    <el-col :span="8" :offset="8" class="loginMain">
      <h1 class="title">健身房会员管理系统</h1>
      <el-form
          ref="ruleFormRef"
          :model="ruleForm"
          status-icon
          :rules="rules"
          label-width="120px"
      >

        <el-form-item label="用户名" prop="username">
          <el-input v-model="ruleForm.username" class="input"/>
        </el-form-item>

        <el-form-item label="密码" prop="pass">
          <el-input v-model="ruleForm.pass" type="password" autocomplete="off" class="input"/>
        </el-form-item>
      </el-form>
      <el-row class="loginButtonRow">
        <el-col :span="8" :offset="8">
          <el-form-item class="loginButton">
            <el-button type="primary" @click="loginForm(ruleFormRef)">登录</el-button>
            <el-button @click="registerForm(ruleFormRef)">注册</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-col>
  </el-row>

</template>

<script lang="ts" setup>
import { reactive, ref } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import axios from "axios";
import router from "@/router";
import store from "@/store";

const ruleFormRef = ref<FormInstance>()

const checkUsername = (rule: any, value: string, callback: any) => {
  if (!value) {
    return callback(new Error('请输入用户名'))
  }
}

const validatePass = (rule: any, value: string, callback: any) => {
  if (value === '') {
    callback(new Error('请输入密码'))
  }
}

const ruleForm = reactive({
  username: 'user1',
  pass: 'password1',
})

const rules = reactive<FormRules<typeof ruleForm>>({
  username: [{ validator: checkUsername, trigger: 'blur' }],
  pass: [{ validator: validatePass, trigger: 'blur' }],

})


const loginForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.validate(async (valid) => {
    if (valid) {
      console.log('登录');
      await axios.post("http://localhost:8080/login", {
        userName: ruleForm.username,
        password: ruleForm.pass,
      }).then((res: any) => {
        console.log(res)
        if (String(res.code) === '1') {
          router.push('/home')
          store.commit('setToken', res.data.data.token)
        }

      })
    } else {
      console.log('注册')
      return false
    }
  })
}

const registerForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}
</script>

<style lang="stylus" scoped>
.loginMain h1 {
  font-size: 25px;
  font-weight: bold;
}

.loginMain {
  margin-top: 300px;
  padding: 30px 30px 20px;
  border: 2px solid grey;
  border-radius: 10px;
  background-color: #fcfcfc;
}

.loginButtonRow {
  margin-top: 30px;
  margin-left: 20px;
}

.input {
  width: 270px;
}
</style>

