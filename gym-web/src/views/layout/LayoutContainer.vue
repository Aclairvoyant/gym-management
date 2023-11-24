<template>
  <div class="common-layout">
    <el-container>
      <el-header>
        <div>
          <!--          <img src="@/assets/default.png" alt="">-->
          <span>健身房会员管理系统</span>
        </div>
      </el-header>
      <el-container class="layout-container">
        <el-aside width="240px">
          <el-row>
            <el-col>
              <div class="el-aside__logo">
                <!--                <img src="@/assets/logoc.png" alt="">-->
              </div>
              <el-menu class="el-menu"
                       background-color="#DCDCDC"
                       :default-active="route.path"
                       router
              >
                <el-menu-item index="/member/list">
                  <el-icon>
                    <UserFilled/>
                  </el-icon>
                  <span>会员管理</span>
                </el-menu-item>
                <el-menu-item index="/coach/list">
                  <el-icon>
                    <Bell />
                  </el-icon>
                  <span>教练管理</span>
                </el-menu-item>
                <el-menu-item index="/course/list">
                  <el-icon>
                    <document/>
                  </el-icon>
                  <span>课程管理</span>
                </el-menu-item>
                <el-menu-item index="/order/list">
                  <el-icon>
                    <Shop />
                  </el-icon>
                  <span>订单查询</span>
                </el-menu-item>
                <el-menu-item index="/user/profile">
                  <el-icon>
                    <setting/>
                  </el-icon>
                  <span>个人中心</span>
                </el-menu-item>
              </el-menu>
            </el-col>
          </el-row>
        </el-aside>
        <el-main>
          <router-view/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import {useUserStore} from '@/stores'
import {onMounted} from 'vue'
import {useRouter} from 'vue-router'
import { useRoute } from 'vue-router'

const route = useRoute()

const userStore = useUserStore()
const router = useRouter()

// onMounted(() => {
//   userStore.getUser()
// })
</script>

<style scoped>
.layout-container {
  height: calc(100vh - 64px);

  .el-aside {
    background-color: #DCDCDC;

    &__logo {
      height: 120px;
      background: url('@/assets/logoc.png') no-repeat center / 120px auto;
    }

    .el-menu {
      border-right: none;
    }
  }
}

.el-header {
  background-color: #101010;

  display: flex;

  justify-content: space-between;

  padding-left: 40px;

  align-items: center;

  color: #fff;

  font-size: 22px;

  > div {

    display: flex;

    align-items: center;

    span {
      margin-left: 15px;
    }
  }

}

</style>
