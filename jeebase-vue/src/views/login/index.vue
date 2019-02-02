<template>
  <div class="login-container">
    <background/>
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on" label-position="left">

      <div class="title-container">
        <img :src="logoImg" class="logo-img">
        <h3 class="title">{{ $t('login.title') }}</h3>
        <!--
        <lang-select class="set-language"></lang-select>-->
      </div>

      <el-form-item prop="userAccount">
        <span class="svg-container svg-container_login">
          <svg-icon icon-class="user" />
        </span>
        <el-input
          v-model="loginForm.userAccount"
          :placeholder="$t('login.userAccount')"
          name="userAccount"
          type="text"
          auto-complete="on"
        />
      </el-form-item>

      <el-form-item prop="userPassword">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input
          :type="passwordType"
          v-model="loginForm.userPassword"
          :placeholder="$t('login.userPassword')"
          name="userPassword"
          auto-complete="on"
          @keyup.enter.native="handleLogin" />
        <span class="show-pwd" @click="showPwd">
          <svg-icon icon-class="eye" />
        </span>
      </el-form-item>

      <el-col :span="15">
        <el-form-item prop="vcode">
          <span class="svg-container">
            <svg-icon icon-class="password" />
          </span>
          <el-input
            v-model="loginForm.vcode"
            :placeholder="$t('login.vcode')"
            name="vcode"
            type="text"
            auto-complete="on"
            @keyup.enter.native="handleLogin" />
        </el-form-item>
      </el-col>
      <el-col :span="9">
        <img :src="vcodeImg" class="v-code-img" @click="changeImgCode">
      </el-col>

      <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:30px;" @click.native.prevent="handleLogin">{{ $t('login.logIn') }}</el-button>

      <div class="tips">
        Jeebase 前后端分离开源整合框架
      </div>
      <div class="tips">
        Copyright © 2012-2018 Jeebase. All Rights Reserved.
      </div>
      <el-input v-show="false" v-model="loginForm.verkey" name="verkey" type="hidden" />
      <!--
      <el-button class="thirdparty-button" type="primary" @click="showDialog=true">{{$t('login.thirdparty')}}</el-button>-->
    </el-form>

    <el-dialog :title="$t('login.thirdparty')" :visible.sync="showDialog" append-to-body>
      {{ $t('login.thirdpartyTips') }}
      <br>
      <br>
      <br>
      <social-sign />
    </el-dialog>

  </div>
</template>

<script>
import { isvalidUserAccount } from '@/utils/validate'
import Background from '@/components/Background'
import LangSelect from '@/components/LangSelect'
import SocialSign from './socialsignin'
import logo from '@/assets/images/logo.png'

export default {
  name: 'Login',
  components: { LangSelect, SocialSign, Background },
  data() {
    const validateUserAccount = (rule, value, callback) => {
      if (!isvalidUserAccount(value)) {
        callback(new Error('请输入账号'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (value.length < 6) {
        callback(new Error('请输入不小于6位的密码'))
      } else {
        callback()
      }
    }
    const validateVCode = (rule, value, callback) => {
      if (value.length < 5) {
        callback(new Error('请输入图片验证码'))
      } else {
        callback()
      }
    }
    return {
      loginForm: {
        userAccount: '',
        userPassword: '',
        vcode: '',
        verkey: ''
      },
      loginRules: {
        userAccount: [{ required: true, trigger: 'blur', validator: validateUserAccount }],
        userPassword: [{ required: true, trigger: 'blur', validator: validatePassword }],
        vcode: [{ required: true, trigger: 'blur', validator: validateVCode }]
      },
      vcodeImg: this.imgCode(),
      passwordType: 'password',
      loading: false,
      showDialog: false,
      logoImg: logo,
      redirect: undefined
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }

  },
  created() {
    // window.addEventListener('hashchange', this.afterQRScan)
  },
  destroyed() {
    // window.removeEventListener('hashchange', this.afterQRScan)
  },
  methods: {
    gRandom() {
      return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1)
    },
    guid() {
      this.verkey = (this.gRandom() + this.gRandom() + '-' + this.gRandom() + '-' + this.gRandom() + '-' + this.gRandom() + '-' + this.gRandom() + this.gRandom() + this.gRandom())
      return this.verkey
    },
    imgCode() {
      return process.env.BASE_API + '/auth/vcode?codeKey=' + this.guid() + '&n=' + Math.random()
    },
    changeImgCode() {
      this.vcodeImg = this.imgCode()
    },
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          this.loginForm.verkey = this.verkey
          this.$store.dispatch('LoginByUserAccount', this.loginForm).then(() => {
            this.loading = false
            this.$router.push({ path: this.redirect || '/' })
          }).catch(() => {
            this.loading = false
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    afterQRScan() {
      // const hash = window.location.hash.slice(1)
      // const hashObj = getQueryObject(hash)
      // const originUrl = window.location.origin
      // history.replaceState({}, '', originUrl)
      // const codeMap = {
      //   wechat: 'code',
      //   tencent: 'code'
      // }
      // const codeName = hashObj[codeMap[this.auth_type]]
      // if (!codeName) {
      //   alert('第三方登录失败')
      // } else {
      //   this.$store.dispatch('LoginByThirdparty', codeName).then(() => {
      //     this.$router.push({ path: '/' })
      //   })
      // }
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
  /* 修复input 背景不协调 和光标变色 */

  $bg:#283443;
  $light_gray:#eee;
  $cursor: #fff;

  @supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
    .login-container .el-input input{
      color: $cursor;
      &::first-line {
        color: $light_gray;
      }
    }
  }

  /* reset element-ui css */
  .login-container {
    .el-input {
      display: inline-block;
      height: 47px;
      width: 85%;
      input {
        background: transparent;
        border: 0px;
        -webkit-appearance: none;
        border-radius: 0px;
        padding: 12px 5px 12px 15px;
        color: $light_gray;
        height: 47px;
        caret-color: $cursor;
        &:-webkit-autofill {
          -webkit-box-shadow: 0 0 0px 1000px $bg inset !important;
          -webkit-text-fill-color: $cursor !important;
        }
      }
    }
    .el-form-item {
      border: 1px solid rgba(255, 255, 255, 0.1);
      background: rgba(0, 0, 0, 0.1);
      border-radius: 5px;
      color: #454545;
    }
  }
</style>

<style rel="stylesheet/scss" lang="scss" scoped>
$bg:#2d3a4b;
$dark_gray:#889aa4;
$light_gray:#eee;
$background: '';

.login-container {
  position: fixed;
  height: 100%;
  width: 100%;
  background:url($background) no-repeat center fixed;
  background-size:100%;
  .login-form {
    position: absolute;
    left:0;
    right: 0;
    width: 520px;
    padding: 35px 35px 15px 35px;
    margin: 8% auto;
    background: rgba(109, 109, 109, 0.23);
    border: 0px solid rgb(221, 222, 225);
    box-shadow: 1px 1px 50px rgba(0,0,0,.3);
  }
  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;
    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }
  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }
  .title-container {
    position: relative;
    .title {
      font-size: 26px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: left;
      font-weight: bold;
      padding-top: 12px;
    }
    .set-language {
      color: #fff;
      position: absolute;
      top: 5px;
      right: 0px;
    }
  }
  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
  .thirdparty-button {
    position: absolute;
    right: 35px;
  }
  .v-code-img {
    height: 47px;
    float: right;
    margin-top: 2px;
    border-radius: 5px;
    cursor: pointer;
    opacity: 0.6;
    filter: alpha(opacity=60);
  }
  .logo-img {
    height: 47px;
    float: left;
    margin-top: 2px;
    border-radius: 5px;
    cursor: pointer;
    margin-left: 76px;
  }
}
</style>
