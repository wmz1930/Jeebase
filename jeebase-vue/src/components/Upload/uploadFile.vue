<template>
  <div class="upload-container">
    <el-button :style="{background:color,borderColor:color}" :size="size" :type="type" icon="el-icon-upload" @click=" dialogVisible=true">{{ btnText }}
    </el-button>
    <el-dialog :visible.sync="dialogVisible" append-to-body style="text-align: center;">
      <el-upload
        :data="dataObj"
        :headers="tokenHeader"
        :multiple="false"
        :file-list="fileList"
        :show-file-list="true"
        :on-remove="handleRemove"
        :on-success="handleSuccess"
        :before-upload="beforeUpload"
        :action="uploadAction"
        :limit="limit"
        class="editor-slide-upload"
        list-type="picture-card">
        <el-button size="small" type="primary">点击上传</el-button>
      </el-upload>
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="handleSubmit">确 定</el-button>
    </el-dialog>
  </div>
</template>

<script>
// import { getToken } from '@/api/qiniu'
import { getToken } from '@/utils/auth'

export default {
  name: 'FileUpload',
  props: {
    color: {
      type: String,
      default: '#1890ff'
    },
    btnText: {
      type: String,
      default: '上传文件'
    },
    size: {
      type: String,
      default: 'mini'
    },
    type: {
      type: String,
      default: 'primary'
    },
    limit: {
      type: Number,
      default: 1
    },
    upurl: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      imgToken: '',
      imgBaseUrl: '',
      dataObj: { token: '' },
      dialogVisible: false,
      listObj: {},
      fileList: [],
      // tokenHeader: { 'Authorization': getToken() },
      uploadAction: process.env.BASE_API + this.upurl
    }
  },
  computed: {
    tokenHeader: function() {
      return { 'Authorization': getToken() }
    }
  },
  created() {
    // return new Promise((resolve, reject) => {
    //   getToken().then(response => {
    //     this.imgToken = response.data.token
    //     this.imgBaseUrl = response.data.baseUrl
    //     resolve(true)
    //   }).catch(err => {
    //     console.log(err)
    //     reject(false)
    //   })
    // })
  },
  methods: {
    checkAllSuccess() {
      return Object.keys(this.listObj).every(item => this.listObj[item].hasSuccess)
    },
    handleSubmit() {
      const arr = Object.keys(this.listObj).map(v => this.listObj[v])
      // if (!this.checkAllSuccess()) {
      //   this.$message('请等待所有文件上传成功 或 出现了网络问题，请刷新页面重新上传！')
      //   return
      // }
      this.$emit('successCBK', arr)
      this.listObj = {}
      this.fileList = []
      this.dialogVisible = false
    },
    handleSuccess(response, file) {
      const uid = file.uid
      const objKeyArr = Object.keys(this.listObj)
      for (let i = 0, len = objKeyArr.length; i < len; i++) {
        if (this.listObj[objKeyArr[i]].uid === uid) {
          this.listObj[objKeyArr[i]].url = this.imgBaseUrl + response.key
          this.listObj[objKeyArr[i]].hasSuccess = true
          return
        }
      }
    },
    handleRemove(file) {
      const uid = file.uid
      const objKeyArr = Object.keys(this.listObj)
      for (let i = 0, len = objKeyArr.length; i < len; i++) {
        if (this.listObj[objKeyArr[i]].uid === uid) {
          delete this.listObj[objKeyArr[i]]
          return
        }
      }
    },
    beforeUpload(file) {
      const _self = this
      const _URL = window.URL || window.webkitURL
      const fileName = file.uid
      this.listObj[fileName] = {}
      _self._data.dataObj.token = this.imgToken
      return new Promise((resolve, reject) => {
        const img = new Image()
        img.src = _URL.createObjectURL(file)
        img.onload = function() {
          _self.listObj[fileName] = { hasSuccess: false, uid: file.uid, width: this.width, height: this.height }
        }
        resolve(true)
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.editor-slide-upload {
  margin-bottom: 20px;
  /deep/ .el-upload--picture-card {
    width: 50%;
  }
}
.upload-container{
  display: inline-block;
}
</style>
