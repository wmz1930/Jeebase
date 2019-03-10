<template>
  <div class="login">
    <background/>
    <div class="login_form">
      <van-row>
        <van-col span="6">
          <img :src="src" class="logo" >
        </van-col>
        <van-col span="18">
          <span class="logo_title">微信服务号登录</span>
        </van-col>
      </van-row>
      <van-cell-group class="form-group">
        <van-field
          v-validate="'required|max:11|phone'"
          v-model.trim="loginForm.mobile"
          :max="11"
          :error="errors.has('mobile')"
          :error-message="errors.first('mobile')"
          name="mobile"
          clearable
          label="手机号"
          left-icon="manager"
          placeholder="请输入手机号"
          maxlength="11"
        />
        <van-field
          v-validate="'required|max:32|min:8'"
          v-model.trim="loginForm.password"
          :min="6"
          :max="32"
          :error="errors.has('password')"
          :error-message="errors.first('password')"
          name="password"
          type="password"
          label="密码"
          left-icon="lock"
          placeholder="请输入8-32位密码"
          maxlength="32"
        />
      </van-cell-group>
      <van-row>
        <van-col span="24" class="login-btn">
          <van-button type="warning" size="large" @click.native="handleLogin">登录</van-button>
        </van-col>
      </van-row>
      <van-row>
        <router-link to="loginMobile" class="forget-btn">忘记密码？</router-link>
      </van-row>
      <van-row>
        <van-col span="24" class="register-btn">
          <router-link :to="registerUrl">
            <van-button plain type="warning" size="small">快速注册</van-button>
          </router-link>
        </van-col>
      </van-row>
      <van-row class="copyright">
        <van-col span="24">
          <span class="copyright-text">©2019 Jeebase</span>
        </van-col>
      </van-row>
    </div>
  </div>
</template>
<script>
import Background from '@/components/Background'
import '@/styles/login.scss'
import logo from '@/assets/images/logo.png'
export default {
  components: {
    Background
  },
  data() {
    return {
      loginForm: {
        mobile: '',
        password: '',
        remember: true
      },
      src: logo,
      redirect: '',
      registerUrl: 'register'
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
        this.registerUrl = 'register'
      },
      immediate: true
    }
  },
  methods: {
    handleLogin() {
      console.log(this.redirect)
      this.$validator.validateAll().then((result) => {
        if (result) {
          this.loading = true
          this.$store.dispatch('LoginByUsername', this.loginForm).then(response => {
            this.loading = false
            this.$router.push({ path: this.redirect || '/member' })
          }).catch(() => {
            this.loading = false
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>
