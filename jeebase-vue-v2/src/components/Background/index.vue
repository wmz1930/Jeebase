<template>
  <div class="starry-sky">
    <canvas ref="canvas" />
  </div>
</template>

<script>
export default {
  name: 'Background',
  props: {
    point: {
      type: Number,
      default: 15 // 生成的星星（点）的个数, 默认25个
    },
    lineColor: {
      type: String,
      default: 'rgba(1,252,254,0.1)' // 线的颜色
    },
    roundColor: {
      type: String,
      default: 'rgba(1,252,254,0.9)' // 星星的颜色
    }
  },
  data() {
    return {
      docWidth: 0, // 画布宽
      docHeight: 0, // 画布高
      context: null, // canvasDom的执行上下文
      circleArr: [], // 圆点数组
      timer: null // 定时器对象
    }
  },
  mounted() {
    const canvasDom = this.$refs.canvas

    // 取画布的高宽来设置显示分辨率
    this.docWidth = canvasDom.offsetWidth
    this.docHeight = canvasDom.offsetHeight

    // 设置画布分辨率
    canvasDom.width = canvasDom.offsetWidth
    canvasDom.height = canvasDom.offsetHeight

    // 初始化canvas上下文
    this.context = canvasDom.getContext('2d')

    // 设置线条和星星颜色
    this.context.strokeStyle = this.lineColor
    this.context.lineWidth = 1
    this.context.fillStyle = this.roundColor

    // 初始化
    this.createCircleArr() // 设置星星数组
    this.draw() // 首屏绘制
    this.cycleDraw() // 循环绘制
  },
  methods: {
    /**
       * 生成min和max之间随机数
       */
    rangeRadom(max, min) {
      return Math.floor(Math.random() * (max - min + 1) + min)
    },
    /**
       * 绘制原点
       */
    drawCircle(context, x, y, r, moveX, moveY) {
      const circle = {
        x,
        y,
        r,
        moveX,
        moveY
      }
      context.beginPath()
      context.arc(circle.x, circle.y, 4, 0, 2 * Math.PI)
      context.closePath()
      context.fill()
      return circle
    },
    /**
       * 绘制线条
       */
    drawLine(context, beginX, beginY, closeX, closeY, opacity) {
      context.beginPath()
      // context.strokeStyle = `rgba(0, 0, 0, ${opacity})`
      context.moveTo(beginX, beginY)
      context.lineTo(closeX, closeY)
      context.closePath()
      context.stroke()
    },
    /**
       * 生成圆点数组
       */
    createCircleArr() {
      for (let i = 0; i < this.point; i++) {
        this.circleArr.push(this.drawCircle(
          this.context,
          this.rangeRadom(this.docWidth, 0),
          this.rangeRadom(this.docHeight, 0),
          this.rangeRadom(15, 2),
          this.rangeRadom(10, -10) / 40,
          this.rangeRadom(10, -10) / 40
        ))
      }
    },
    /**
       * 每一帧绘制
       */
    draw() {
      const circleArr = this.circleArr
      this.context.clearRect(0, 0, this.docWidth, this.docHeight)
      // 循环绘制点
      for (let i = 0; i < this.point; i++) {
        this.drawCircle(this.context, circleArr[i].x, circleArr[i].y, circleArr[i].r)
      }
      // 循环绘制线
      for (let i = 0; i < this.point; i++) {
        for (let j = 0; j < this.point; j++) {
          if (i + j < this.point) {
            const A = Math.abs(circleArr[i + j].x - circleArr[i].x)
            const B = Math.abs(circleArr[i + j].y - circleArr[i].y)
            const lineLength = Math.sqrt(A * A + B * B)
            const C = 1 / lineLength * 7 - 0.009
            const lineOpacity = C > 0.03 ? 0.03 : C
            if (lineOpacity > 0) {
              this.drawLine(
                this.context,
                circleArr[i].x,
                circleArr[i].y,
                circleArr[i + j].x,
                circleArr[i + j].y,
                lineOpacity,
              )
            }
          }
        }
      }
    },
    /**
       * 循环绘制
       */
    cycleDraw() {
      this.timer = setInterval(() => {
        for (let i = 0; i < this.point; i++) {
          const cir = this.circleArr[i]
          cir.x += cir.moveX
          cir.y += cir.moveY
          if (cir.x > this.docWidth) {
            cir.x = 0
          } else if (cir.x < 0) {
            cir.x = this.docWidth
          }
          if (cir.y > this.docHeight) {
            cir.y = 0
          } else if (cir.y < 0) {
            cir.y = this.docHeight
          }
        }
        this.draw()
      }, 10)
    }
  },
  beforeDestory() {
    // 记得摧毁定时器
    clearInterval(this.timer)
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
$background: 'bg.png';
  .starry-sky {
    width: 100%;
    height: 100%;
    position:absolute;
    background:url($background) no-repeat center fixed;
    background-size:100%;
    z-index:-2;
    > canvas {
      width: 100%;
      height: 100%;
    }
  }
</style>
