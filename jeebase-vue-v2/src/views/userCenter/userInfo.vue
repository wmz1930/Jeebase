<template>
  <div class="components-container">
    <el-form ref="userForm" :model="userForm" ules="rules" label-width="100px" class="userForm" style="width: 500px; margin-left:50px;">
      <el-form-item label="用户头像">
        <my-upload
          v-model="show"
          :width="300"
          :height="300"
          :params="params"
          :headers="headers"
          field="file"
          url="http://upload.qiniup.com"
          img-format="png"
          @crop-success="cropSuccess"
          @crop-upload-success="cropUploadSuccess"
          @crop-upload-fail="cropUploadFail"
        />
        <img :src="userForm.headImgUrl" class="avatar" title="点击修改头像" @click="toggleShow">
      </el-form-item>
      <el-form-item :label="$t('userTable.userAccount')" prop="userAccount">
        <label>{{ userForm.userAccount }}</label>
      </el-form-item>
      <el-form-item :label="$t('userTable.userMobile')" prop="userMobile">
        <label>{{ userForm.userMobile }}</label>
      </el-form-item>
      <el-form-item :label="$t('userTable.userEmail')" prop="userEmail">
        <label>{{ userForm.userEmail }}</label>
      </el-form-item>
      <el-form-item :label="$t('userTable.roleName')" prop="roleName">
        <label>{{ userForm.roleName }}</label>
      </el-form-item>
      <el-form-item :label="$t('userTable.userNickName')" prop="userNickName">
        <el-input v-model="userForm.userNickName" placeholder="输入用户昵称" maxlength="32" />
      </el-form-item>
      <el-form-item :label="$t('userTable.userName')" prop="userName">
        <el-input v-model="userForm.userName" placeholder="输入用户姓名" maxlength="32" />
      </el-form-item>
      <el-form-item :label="$t('userTable.area')" prop="province">
        <el-cascader v-model="userForm.areas" :options="provinceOptions" :props="props" filterable change-on-select style="width:100%;" />
      </el-form-item>
      <el-form-item :label="$t('userTable.userSex')" prop="userSex">
        <el-radio-group v-model="userForm.userSex">
          <el-radio :label="1">男性</el-radio>
          <el-radio :label="0">女性</el-radio>
          <el-radio :label="2">保密</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item class="btn-submit">
        <el-button>取消</el-button>
        <el-button type="primary" @click="updateData">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import 'babel-polyfill' // es6 shim
import myUpload from 'vue-image-crop-upload'
import { getToken } from '@/api/qiniu'
import { queryUserInfo, updateUserInfo } from '@/api/system/user'
import Data from '@/api/pcaa'

export default {
  name: 'UserInfo',
  components: { myUpload },
  data() {
    return {
      userForm: {
        id: '',
        userAccount: '',
        userNickName: '',
        userName: '',
        userMobile: '',
        userEmail: '',
        roleName: '',
        userSex: 1,
        headImgUrl: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/150/h/150',
        areas: [],
        description: ''
      },
      rules: {
        userNickName: [
          { min: 2, max: 16, message: '长度在 2 到 16 个字符', trigger: 'blur' }
        ],
        userName: [
          { required: true, message: '请输入用户姓名', trigger: 'blur' },
          { min: 2, max: 16, message: '长度在 2 到 16 个字符', trigger: 'blur' }
        ]
      },
      show: false,
      baseUrl: '',
      params: {
        token: ''
      },
      provinceOptions: null,
      props: {
        children: 'children'
      },
      headers: {
        smail: '*_~'
      }
    }
  },
  created() {
    this.getAreaList()
    this.queryData()
  },
  methods: {
    toggleShow() {
      const _self = this
      return new Promise((resolve, reject) => {
        getToken().then(response => {
          _self.show = !_self.show
          const token = response.data.token
          _self._data.params.token = token
          _self.baseUrl = response.data.baseUrl
          resolve(true)
        }).catch(err => {
          console.log(err)
          reject(false)
        })
      })
    },
    /**
			 * crop success
			 *
			 * [param] imgDataUrl
			 * [param] field
			 */
    cropSuccess(imgDataUrl, field) {
      this.userForm.headImgUrl = imgDataUrl
    },
    /**
			 * upload success
			 *
			 * [param] jsonData  server api return data, already json encode
			 * [param] field
			 */
    cropUploadSuccess(jsonData, field) {
      this.userForm.headImgUrl = this.baseUrl + jsonData.key
    },
    /**
			 * upload fail
			 *
			 * [param] status    server api return error status, like 500
			 * [param] field
			 */
    cropUploadFail(status, field) {
      this.userForm.headImgUrl = ''
    },
    queryData() {
      queryUserInfo().then((res) => {
        this.userForm = Object.assign({}, res.data)
        this.userForm.areas = [res.data.province, res.data.city, res.data.area]
        this.userForm.userSex = parseInt(res.data.userSex)
      })
    },
    updateData() {
      this.$refs['userForm'].validate((valid) => {
        if (valid) {
          updateUserInfo(this.userForm).then(() => {
            this.$message({
              message: '个人信息修改成功',
              type: 'success'
            })
          })
        }
      })
    },
    getAreaList() {
      var options = []
      for (var key in Data['86']) {
        var citys = []
        for (var keyc in Data[key]) {
          var areas = []
          for (var keya in Data[keyc]) {
            var area = { 'value': keya, 'label': Data[keyc][keya] }
            areas.push(area)
          }
          var city = { 'value': keyc, 'label': Data[key][keyc], 'children': areas }
          citys.push(city)
        }
        var province = { 'value': key, 'label': Data['86'][key], 'children': citys }
        options.push(province)
      }
      this.provinceOptions = options
    }
  }
}
</script>

<style scoped>
  .avatar{
    width: 150px;
    height: 150px;
    border-radius: 10px;
    cursor: pointer;
  }
  .btn-submit{
    float: right;
  }
</style>

