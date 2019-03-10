<template>
  <div class="index_page">
    <!--搜索框-->
    <van-row
      class="search-bar"
      style="position:fixed;background:transparent;width:100%;z-index:1;line-height: 2.1rem;">
      <van-col span="3" style="text-align: center;">
        <svg-icon
          icon-class="classification"
          style="font-size: 0.85rem;color:#fff;"/>
      </van-col>
      <van-col span="18">
        <van-cell-group style="border-radius: 50px;display: inline-block;width: 100%;background: transparent;">
          <van-field v-model="value" left-icon="search" placeholder="搜索" />
        </van-cell-group>
      </van-col>
      <van-col span="3" style="text-align: center;">
        <svg-icon
          icon-class="member"
          style="font-size: 0.85rem;color:#fff;" />
      </van-col>
    </van-row>
    <!--轮播图-->
    <van-swipe :autoplay="3000" class="index_page-swipe" style="height: 7.36rem;">
      <van-swipe-item v-for="thumb in index_page.thumb" :key="thumb">
        <img :src="thumb" >
      </van-swipe-item>
    </van-swipe>
    <!--图标导航-->
    <van-row gutter="" style="margin-top: 0.5rem;padding-top: 0.5rem;">
      <van-col span="6" style="text-align:center;">
        <div class="van-tabbar-item">
          <div class="van-tabbar-item__icon">
            <svg-icon
              icon-class="super-market"
              style="font-size: 1.5rem;"/>
          </div>
          <div class="van-tabbar-item__text">
            <span style="color: rgb(102, 102, 102);">超市</span>
          </div>
        </div>
      </van-col>
      <van-col span="6" style="text-align:center;">
        <div class="van-tabbar-item">
          <div class="van-tabbar-item__icon">
            <svg-icon
              icon-class="tea"
              style="font-size: 1.5rem;"/>
          </div>
          <div class="van-tabbar-item__text">
            <span style="color: rgb(102, 102, 102);">茶饮</span>
          </div>
        </div>
      </van-col>
      <van-col span="6" style="text-align:center;">
        <div class="van-tabbar-item">
          <div class="van-tabbar-item__icon">
            <svg-icon
              icon-class="clothing"
              style="font-size: 1.5rem;"/>
          </div>
          <div class="van-tabbar-item__text">
            <span style="color: rgb(102, 102, 102);">服饰</span>
          </div>
        </div>
      </van-col>
      <van-col span="6" style="text-align:center;">
        <div class="van-tabbar-item">
          <div class="van-tabbar-item__icon">
            <svg-icon
              icon-class="makeup"
              style="font-size: 1.5rem;"/>
          </div>
          <div class="van-tabbar-item__text">
            <span style="color: rgb(102, 102, 102);">美妆</span>
          </div>
        </div>
      </van-col>
    </van-row>
    <van-row gutter="" style="padding: 0.5rem 0;">
      <van-col span="6" style="text-align:center;">
        <div class="van-tabbar-item">
          <div class="van-tabbar-item__icon">
            <svg-icon
              icon-class="kill"
              style="font-size: 1.5rem;"/>
          </div>
          <div class="van-tabbar-item__text">
            <span style="color: rgb(102, 102, 102);">秒杀</span>
          </div>
        </div>
      </van-col>
      <van-col span="6" style="text-align:center;">
        <div class="van-tabbar-item">
          <div class="van-tabbar-item__icon">
            <svg-icon
              icon-class="group"
              style="font-size: 1.5rem;"/>
          </div>
          <div class="van-tabbar-item__text">
            <span style="color: rgb(102, 102, 102);">团购</span>
          </div>
        </div>
      </van-col>
      <van-col span="6" style="text-align:center;">
        <div class="van-tabbar-item">
          <div class="van-tabbar-item__icon">
            <svg-icon
              icon-class="appliances"
              style="font-size: 1.5rem;"/>
          </div>
          <div class="van-tabbar-item__text">
            <span style="color: rgb(102, 102, 102);">家电</span>
          </div>
        </div>
      </van-col>
      <van-col span="6" style="text-align:center;">
        <div class="van-tabbar-item">
          <div class="van-tabbar-item__icon">
            <svg-icon
              icon-class="sign"
              style="font-size: 1.5rem;"/>
          </div>
          <div class="van-tabbar-item__text">
            <span style="color: rgb(102, 102, 102);">签到</span>
          </div>
        </div>
      </van-col>
    </van-row>
    <van-row style="background: #fff;border-radius: 0.2rem;width: 95%;margin-left: auto;margin-right: auto;">
      <van-col span="4" style="text-align: center;">
        <img style="padding: 0.15rem 0px;display: inline-block;height:0.7rem;" src="http://img.jeebase.com/kuaibao.jpg">
      </van-col>
      <van-col span="17">
        <div class="text-container">
          <transition class="inner-container2" name="slide" mode="out-in">
            <span :key="text.id" class="text2"><span style="color:#f23030;">HOT</span> {{ text.val }}</span>
          </transition>
        </div>
      </van-col>
      <van-col span="3" style="padding: 0.15rem 0px;display: inline-block;">
        <span style="border-left: 1px solid rgb(204, 204, 204);padding-left: 0.25rem;">更多</span>
      </van-col>
    </van-row>

    <!--免邮拼团-->
    <van-row style="margin-top:0.5rem;">
      <van-cell title="免邮拼团" is-link url="//youzan.github.io/vant/mobile.html" value="更多"/>
      <van-swipe :autoplay="4000" indicator-color="white">
        <van-swipe-item>
          <van-row gutter="1">
            <van-col span="12">
              <van-card
                thumb="http://img.jeebase.com/xiaomi_che.png"
                tag="标签"
                price="2.00"
                desc="白色 Ninebot 9号体感车 双轮长续航蓝牙遥控 轻小便携载重王 智能成人平衡车"
                title=" 小米（MI）9号平衡车 白色 Ninebot 9号体感车 双轮长续航蓝牙遥控 轻小便携载重王 智能成人平衡车 "
                origin-price="10.00"
              />
            </van-col>
            <van-col span="12">
              <van-card
                thumb="http://img.jeebase.com/xiaomi_che.png"
                tag="标签"
                price="2.00"
                desc="白色 Ninebot 9号体感车 双轮长续航蓝牙遥控 轻小便携载重王 智能成人平衡车"
                title=" 小米（MI）9号平衡车 白色 Ninebot 9号体感车 双轮长续航蓝牙遥控 轻小便携载重王 智能成人平衡车 "
                origin-price="10.00"
              />
            </van-col>
          </van-row>
        </van-swipe-item>
        <van-swipe-item>
          <van-row gutter="1">
            <van-col span="12">
              <van-card
                thumb="http://img.jeebase.com/xiaomi_che.png"
                tag="标签"
                price="2.00"
                desc="白色 Ninebot 9号体感车 双轮长续航蓝牙遥控 轻小便携载重王 智能成人平衡车"
                title=" 小米（MI）9号平衡车 白色 Ninebot 9号体感车 双轮长续航蓝牙遥控 轻小便携载重王 智能成人平衡车 "
                origin-price="10.00"
              />
            </van-col>
            <van-col span="12">
              <van-card
                thumb="http://img.jeebase.com/xiaomi_che.png"
                tag="标签"
                price="2.00"
                desc="白色 Ninebot 9号体感车 双轮长续航蓝牙遥控 轻小便携载重王 智能成人平衡车"
                title=" 小米（MI）9号平衡车 白色 Ninebot 9号体感车 双轮长续航蓝牙遥控 轻小便携载重王 智能成人平衡车 "
                origin-price="10.00"
              />
            </van-col>
          </van-row>
        </van-swipe-item>
        <van-swipe-item>
          <van-row gutter="1">
            <van-col span="12">
              <van-card
                thumb="http://img.jeebase.com/xiaomi_che.png"
                tag="标签"
                price="2.00"
                desc="白色 Ninebot 9号体感车 双轮长续航蓝牙遥控 轻小便携载重王 智能成人平衡车"
                title=" 小米（MI）9号平衡车 白色 Ninebot 9号体感车 双轮长续航蓝牙遥控 轻小便携载重王 智能成人平衡车 "
                origin-price="10.00"
              />
            </van-col>
            <van-col span="12">
              <van-card
                thumb="http://img.jeebase.com/xiaomi_che.png"
                tag="标签"
                price="2.00"
                desc="白色 Ninebot 9号体感车 双轮长续航蓝牙遥控 轻小便携载重王 智能成人平衡车"
                title=" 小米（MI）9号平衡车 白色 Ninebot 9号体感车 双轮长续航蓝牙遥控 轻小便携载重王 智能成人平衡车 "
                origin-price="10.00"
              />
            </van-col>
          </van-row>
        </van-swipe-item>
        <van-swipe-item>
          <van-row gutter="1">
            <van-col span="12">
              <van-card
                thumb="http://img.jeebase.com/xiaomi_che.png"
                tag="标签"
                price="2.00"
                desc="白色 Ninebot 9号体感车 双轮长续航蓝牙遥控 轻小便携载重王 智能成人平衡车"
                title=" 小米（MI）9号平衡车 白色 Ninebot 9号体感车 双轮长续航蓝牙遥控 轻小便携载重王 智能成人平衡车 "
                origin-price="10.00"
              />
            </van-col>
            <van-col span="12">
              <van-card
                thumb="http://img.jeebase.com/xiaomi_che.png"
                tag="标签"
                price="2.00"
                desc="白色 Ninebot 9号体感车 双轮长续航蓝牙遥控 轻小便携载重王 智能成人平衡车"
                title=" 小米（MI）9号平衡车 白色 Ninebot 9号体感车 双轮长续航蓝牙遥控 轻小便携载重王 智能成人平衡车 "
                origin-price="10.00"
              />
            </van-col>
          </van-row>
        </van-swipe-item>
      </van-swipe>
    </van-row>

    <!--人气推荐-->
    <van-row style="margin-top:0.5rem;">
      <van-cell title="人气推荐" is-link url="//youzan.github.io/vant/mobile.html" value="更多"/>
      <van-row gutter="">
        <van-col span="24">
          <van-card
            thumb="http://img.jeebase.com/xiaomi_che.png"
            tag="标签"
            price="2.00"
            desc="白色 Ninebot 9号体感车 双轮长续航蓝牙遥控 轻小便携载重王 智能成人平衡车"
            title=" 小米（MI）9号平衡车 白色 Ninebot 9号体感车 双轮长续航蓝牙遥控 轻小便携载重王 智能成人平衡车 "
            origin-price="10.00"
          />
        </van-col>
      </van-row>
      <van-row gutter="" style="padding: 0.5rem 0;">
        <van-col span="8" style="text-align:center;">
          <div class="van-tabbar-item">
            <div class="van-tabbar-item__icon">
              <svg-icon
                icon-class="kill"
                style="font-size: 1.5rem;"/>
            </div>
            <div class="van-tabbar-item__text">
              <span style="color: rgb(102, 102, 102);">秒杀</span>
            </div>
          </div>
        </van-col>
        <van-col span="8" style="text-align:center;">
          <div class="van-tabbar-item">
            <div class="van-tabbar-item__icon">
              <svg-icon
                icon-class="group"
                style="font-size: 1.5rem;"/>
            </div>
            <div class="van-tabbar-item__text">
              <span style="color: rgb(102, 102, 102);">团购</span>
            </div>
          </div>
        </van-col>
        <van-col span="8" style="text-align:center;">
          <div class="van-tabbar-item">
            <div class="van-tabbar-item__icon">
              <svg-icon
                icon-class="appliances"
                style="font-size: 1.5rem;"/>
            </div>
            <div class="van-tabbar-item__text">
              <span style="color: rgb(102, 102, 102);">家电</span>
            </div>
          </div>
        </van-col>
      </van-row>
    </van-row>

    <!--限时秒杀-->
    <van-row style="margin-top:0.5rem;">
      <van-cell title="限时秒杀" is-link url="//youzan.github.io/vant/mobile.html" value="更多"/>
      <van-swipe :autoplay="5000" indicator-color="white">
        <van-swipe-item>
          <van-row gutter="1">
            <van-col span="12">
              <van-card
                thumb="http://img.jeebase.com/xiaomi_che.png"
                tag="标签"
                price="2.00"
                desc="白色 Ninebot 9号体感车 双轮长续航蓝牙遥控 轻小便携载重王 智能成人平衡车"
                title=" 小米（MI）9号平衡车 白色 Ninebot 9号体感车 双轮长续航蓝牙遥控 轻小便携载重王 智能成人平衡车 "
                origin-price="10.00"
              />
            </van-col>
            <van-col span="12">
              <van-card
                thumb="http://img.jeebase.com/xiaomi_che.png"
                tag="标签"
                price="2.00"
                desc="白色 Ninebot 9号体感车 双轮长续航蓝牙遥控 轻小便携载重王 智能成人平衡车"
                title=" 小米（MI）9号平衡车 白色 Ninebot 9号体感车 双轮长续航蓝牙遥控 轻小便携载重王 智能成人平衡车 "
                origin-price="10.00"
              />
            </van-col>
          </van-row>
        </van-swipe-item>
        <van-swipe-item>
          <van-row gutter="1">
            <van-col span="12">
              <van-card
                thumb="http://img.jeebase.com/xiaomi_che.png"
                tag="标签"
                price="2.00"
                desc="白色 Ninebot 9号体感车 双轮长续航蓝牙遥控 轻小便携载重王 智能成人平衡车"
                title=" 小米（MI）9号平衡车 白色 Ninebot 9号体感车 双轮长续航蓝牙遥控 轻小便携载重王 智能成人平衡车 "
                origin-price="10.00"
              />
            </van-col>
            <van-col span="12">
              <van-card
                thumb="http://img.jeebase.com/xiaomi_che.png"
                tag="标签"
                price="2.00"
                desc="白色 Ninebot 9号体感车 双轮长续航蓝牙遥控 轻小便携载重王 智能成人平衡车"
                title=" 小米（MI）9号平衡车 白色 Ninebot 9号体感车 双轮长续航蓝牙遥控 轻小便携载重王 智能成人平衡车 "
                origin-price="10.00"
              />
            </van-col>
          </van-row>
        </van-swipe-item>
        <van-swipe-item>
          <van-row gutter="1">
            <van-col span="12">
              <van-card
                thumb="http://img.jeebase.com/xiaomi_che.png"
                tag="标签"
                price="2.00"
                desc="白色 Ninebot 9号体感车 双轮长续航蓝牙遥控 轻小便携载重王 智能成人平衡车"
                title=" 小米（MI）9号平衡车 白色 Ninebot 9号体感车 双轮长续航蓝牙遥控 轻小便携载重王 智能成人平衡车 "
                origin-price="10.00"
              />
            </van-col>
            <van-col span="12">
              <van-card
                thumb="http://img.jeebase.com/xiaomi_che.png"
                tag="标签"
                price="2.00"
                desc="白色 Ninebot 9号体感车 双轮长续航蓝牙遥控 轻小便携载重王 智能成人平衡车"
                title=" 小米（MI）9号平衡车 白色 Ninebot 9号体感车 双轮长续航蓝牙遥控 轻小便携载重王 智能成人平衡车 "
                origin-price="10.00"
              />
            </van-col>
          </van-row>
        </van-swipe-item>
        <van-swipe-item>
          <van-row gutter="1">
            <van-col span="12">
              <van-card
                thumb="http://img.jeebase.com/xiaomi_che.png"
                tag="标签"
                price="2.00"
                desc="白色 Ninebot 9号体感车 双轮长续航蓝牙遥控 轻小便携载重王 智能成人平衡车"
                title=" 小米（MI）9号平衡车 白色 Ninebot 9号体感车 双轮长续航蓝牙遥控 轻小便携载重王 智能成人平衡车 "
                origin-price="10.00"
              />
            </van-col>
            <van-col span="12">
              <van-card
                thumb="http://img.jeebase.com/xiaomi_che.png"
                tag="标签"
                price="2.00"
                desc="白色 Ninebot 9号体感车 双轮长续航蓝牙遥控 轻小便携载重王 智能成人平衡车"
                title=" 小米（MI）9号平衡车 白色 Ninebot 9号体感车 双轮长续航蓝牙遥控 轻小便携载重王 智能成人平衡车 "
                origin-price="10.00"
              />
            </van-col>
          </van-row>
        </van-swipe-item>
      </van-swipe>
    </van-row>

    <!--底部导航-->
    <van-tabbar
      v-model="active"
      active-color="#ff976a"
    >
      <van-tabbar-item>
        <span>首页</span>
        <svg-icon
          slot="icon"
          icon-class="index" />
      </van-tabbar-item>
      <van-tabbar-item>
        <span>分类</span>
        <svg-icon
          slot="icon"
          icon-class="classification" />
      </van-tabbar-item>
      <van-tabbar-item info="">
        <span>购物车</span>
        <svg-icon
          slot="icon"
          icon-class="cart" />
      </van-tabbar-item>
      <van-tabbar-item>
        <span>我的</span>
        <svg-icon
          slot="icon"
          icon-class="member" />
      </van-tabbar-item>
    </van-tabbar>
  </div>
</template>

<script>
import { Tabbar, TabbarItem, Toast } from 'vant'
const totalDuration = 3000
export default {
  components: {
    Tabbar,
    TabbarItem,
    Toast
  },

  data() {
    return {
      active: 0,
      value: '',
      icon: {
        normal: '//img.yzcdn.cn/icon-normal.png',
        active: '//img.yzcdn.cn/icon-active.png'
      },
      index_page: {
        title: '美国伽力果（约680g/3个）',
        price: 2680,
        express: '免运费',
        remain: 19,
        thumb: [
          'http://img.jeebase.com/mall_banner_1.jpg',
          'http://img.jeebase.com/mall_banner_2.jpg',
          'http://img.jeebase.com/mall_banner_3.jpg',
          'http://img.jeebase.com/mall_banner_4.jpg',
          'http://img.jeebase.com/mall_banner_5.jpg'
        ]
      },
      arr: [
        '不是被郭德纲发现的，也不是一开始就收为徒弟。',
        '最新 现在雅阁这个状态像极了新A4L上市那段日子。',
        '最新 低配太寒碜，各种需要加装，中配定价过高，又没啥特色',
        '最新 然后各种机油门、经销商造反什么的幺蛾子。',
        '最新 看五月销量，建议参考A4，打8折吧。',
        '最新 不是被郭德纲发现的，也不是一开始就收为徒弟。'
      ],
      number: 0
    }
  },
  computed: {
    text() {
      return {
        id: this.number,
        val: this.arr[this.number]
      }
    }
  },
  mounted() {
    this.startMove()
  },
  methods: {
    formatPrice() {
      return '¥' + (this.index_page.price / 100).toFixed(2)
    },

    onClickCart() {
      this.$router.push('cart')
    },

    sorry() {
      Toast('暂无后续逻辑~')
    },
    startMove() {
      setTimeout(() => {
        if (this.number === 5) {
          this.number = 0
        } else {
          this.number += 1
        }
        this.startMove()
      }, totalDuration)
    }
  }
}
</script>

<style lang="less">
.search-bar .van-cell {
    padding: 0.18rem 0.5rem;
    line-height: 0.9rem;
    border-radius: 0.6rem;
    background: rgba(255,255,255,0.8);
    font-size: .48rem;
    color: #999;
}
.van-hairline--top-bottom::after {
    border-width: 0;
}

.index_page {
  padding-bottom: 0.6rem;
  &-swipe {
    img {
      width: 100%;
      height: 7.36rem;
      display: block;
    }
  }

  &-title {
    font-size: 16px;
  }

  &-price {
    color: #f44;
  }

  &-express {
    color: #999;
    font-size: 12px;
    padding: 5px 15px;
  }

  &-cell-group {
    margin: 15px 0;

    .van-cell__value {
      color: #999;
    }
  }

  &-tag {
    margin-left: 5px;
  }

  h2 {
    margin: 20px 0
  }
  .text-container {
    width: 100%;
    margin: 0 auto;
    padding: 0.15rem 0;
    overflow: hidden;
  }
  .text, .text2 {
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 1;// 限制快级元素的文本行数
    overflow: hidden;
    margin: 0;
  }
  .inner-container {
    animation: myMove 8s linear infinite;
    animation-fill-mode: forwards;
  }
  /*文字无缝滚动*/
  @keyframes myMove {
    0% {
      transform: translateY(0);
    }
    100% {
      transform: translateY(-150px);
    }
  }
  /*文字停顿滚动*/
  @keyframes myMove2 {
    0% {
      transform: translateY(0);
    }
    10% {
      transform: translateY(-30px);
    }
    20% {
      transform: translateY(-30px);
    }
    30% {
      transform: translateY(-60px);
    }
    40% {
      transform: translateY(-60px);
    }
    50% {
      transform: translateY(-90px);
    }
    60% {
      transform: translateY(-90px);
    }
    70% {
      transform: translateY(-120px);
    }
    80% {
      transform: translateY(-120px);
    }
    90% {
      transform: translateY(-150px);
    }
    100% {
      transform: translateY(-150px);
    }
  }
  .slide-enter-active, .slide-leave-active {
    transition: all 0.3s linear;
  }
  .slide-leave-to {
    transform: translateY(-20px);
  }
  .slide-enter{
    transform: translateY(20px);
  }
}
</style>
