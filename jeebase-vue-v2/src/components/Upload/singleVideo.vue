<template>
  <div class="upload-container">
    <div class="image-preview image-app-preview">
      <div v-if="!pass && progress !== 0 && progress !== 100" class="img-progress">
        <el-progress :percentage="progress" :status="proStatus" type="circle"/>
      </div>
      <div v-if="!(!pass && progress !== 0 && progress !== 100)" v-show="itemPicUrl.length>1" class="image-preview-wrapper">
        <video-player
          ref="videoPlayer"
          :playsinline="false"
          :options="playerOptions"
          class="video-player vjs-custom-skin"
          @play="onPlayerPlay($event)"
          @pause="onPlayerPause($event)"
          @statechanged="playerStateChanged($event)"/>
          <!--
        <div class="image-preview-action">
          <i @click="rmImage" class="el-icon-delete"></i>
        </div>-->
      </div>
    </div>
    <el-upload
      :data="dataObj"
      :multiple="false"
      :show-file-list="false"
      :on-success="handleImageScucess"
      :before-upload="beforeUpload"
      :on-change="uploadOnChange"
      :on-error="uploadOnError"
      :on-progress="uploadOnProgress"
      class="image-uploader"
      drag
      action="http://upload.qiniup.com">
      <i class="el-icon-upload"/>
      <div class="el-upload__text">将视频拖到此处，或<em>点击上传</em></div>
    </el-upload>
  </div>
</template>

<script>
import { getToken } from '@/api/qiniu'
import { videoPlayer } from 'vue-video-player'
import '../../../node_modules/video.js/dist/video-js.css'
import '../../../node_modules/vue-video-player/src/custom-theme.css'

export default {
  name: 'SingleVideoUpload',
  components: {
    videoPlayer
  },
  props: {
    value: {
      type: String,
      default: ''
    },
    videoUrl: {
      type: String,
      default: ''
    },
    state: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      tempUrl: '',
      dataObj: { token: '' },
      progress: 0, // 上传进度
      pass: null, // 是否上传成功
      playerOptions: {
        // playbackRates: [0.7, 1.0, 1.5, 2.0], // 播放速度
        autoplay: false, // 如果true,浏览器准备好时开始回放。
        muted: false, // 默认情况下将会消除任何音频。
        loop: false, // 导致视频一结束就重新开始。
        preload: 'auto', // 建议浏览器在<video>加载元素后是否应该开始下载视频数据。auto浏览器选择最佳行为,立即开始加载视频（如果浏览器支持）
        language: 'zh-CN',
        aspectRatio: '16:9', // 将播放器置于流畅模式，并在计算播放器的动态大小时使用该值。值应该代表一个比例 - 用冒号分隔的两个数字（例如"16:9"或"4:3"）
        fluid: true, // 当true时，Video.js player将拥有流体大小。换句话说，它将按比例缩放以适应其容器。
        sources: [{
          type: 'video/mp4',
          src: ''// 你的m3u8地址（必填）
        }],
        // poster: 'https://surmon-china.github.io/vue-quill-editor/static/images/surmon-3.jpg', // 你的封面地址
        width: document.documentElement.clientWidth,
        notSupportedMessage: '此视频暂无法播放，请稍后再试'// 允许覆盖Video.js无法播放媒体源时显示的默认信息。
      }
    }
  },
  computed: {
    itemPicUrl() {
      return this.value
    },
    proStatus() { // 上传状态
      if (this.pass) {
        return 'success'
      } else if (this.pass === false) {
        return 'exception'
      } else {
        return ''
      }
    },
    player() {
      return this.$refs.videoPlayer.player
    }
  },
  watch: {
    // 更改视频源 videoUrl从弹出框组件传值
    videoUrl: function(val) {
      if (val !== '') {
        this.$refs.videoPlayer.player.src(val)
      }
    },
    // 弹出框关闭后暂停 否则一直在播放 state从弹出框组件传值

    state: function(val) {
      if (val) {
        this.$refs.videoPlayer.player.pause()
      }
    }
  },
  methods: {
    rmImage() {
      this.emitInput('')
    },
    emitInput(val) {
      this.$emit('input', val)
    },
    handleImageScucess(res, file) {
      console.log(res)
      this.emitInput(this.tempUrl + res.key)
      this.playerOptions.sources[0].src = this.tempUrl + res.key
    },
    beforeUpload() {
      const _self = this
      return new Promise((resolve, reject) => {
        getToken().then(response => {
          const token = response.data.token
          _self._data.dataObj.token = token
          this.tempUrl = response.data.baseUrl
          resolve(true)
        }).catch(err => {
          console.log(err)
          reject(false)
        })
      })
    },
    uploadOnProgress(e, file) { // 开始上传
      this.progress = Math.floor(e.percent)
    },
    uploadOnChange(file) {
      if (file.status === 'ready') {
        console.log('ready')
      } else if (file.status === 'fail') {
        this.$message.error('图片上传出错，请刷新重试！')
      }
    },
    uploadOnError(e, file) {
      console.log(e)
    },
    onPlayerPlay(player) {
    },
    onPlayerPause(player) {
    },
    playerStateChanged(player) {
    }
  }

}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
@import "src/styles/mixin.scss";
.upload-container {
  width: 100%;
  position: relative;
  @include clearfix;
  .image-uploader {
    width: 16%;
    float: left;
    margin-left: 50px;
  }
  .image-preview {
    width: 200px;
    height: 200px;
    position: relative;
    border: 1px dashed #d9d9d9;
    float: left;
    margin-left: 50px;
    .image-preview-wrapper {
      position: relative;
      width: 100%;
      height: 100%;
      img {
        width: 100%;
        height: 100%;
      }
    }
    .image-preview-action {
      position: absolute;
      width: 100%;
      height: 100%;
      left: 0;
      top: 0;
      cursor: default;
      text-align: center;
      color: #fff;
      opacity: 0;
      font-size: 20px;
      background-color: rgba(0, 0, 0, .5);
      transition: opacity .3s;
      cursor: pointer;
      text-align: center;
      line-height: 200px;
      .el-icon-delete {
        font-size: 36px;
      }
    }
    &:hover {
      .image-preview-action {
        opacity: 1;
      }
    }
  }
  .image-app-preview {
    width: 320px;
    height: 180px;
    position: relative;
    border: 1px dashed #d9d9d9;
    float: left;
    margin-left: 50px;
    .app-fake-conver {
      height: 44px;
      position: absolute;
      width: 100%; // background: rgba(0, 0, 0, .1);
      text-align: center;
      line-height: 64px;
      color: #fff;
    }
  }
  .img-progress {
    vertical-align:middle;
    text-align:center;
    padding-top: 8%;
  }
  .img-progress-two {
    vertical-align:middle;
    text-align:center;
    padding-top: 18%;
  }
}
</style>
