<template>
    <div class="wrapper" @click="update">
        <image :src="logoUrl" class="logo"></image>
        <text class="title">Hello {{target}}</text>
        <button value="修改头像" @click.native="modifyHead" type="success" size="middle" style="margin-top:12px;"></button>
        <image :src="headUrl" class="head"></image>
    </div>
</template>

<style>
  .wrapper {align-items: center; margin-top: 120px;}
  .title {font-size: 48px;}
  .logo {width: 360px; height: 82px;}
  .head {width: 500px; height: 500px;}


</style>

<script>
  var modal = weex.requireModule('modal')
  module.exports = {
    data: {
      logoUrl: 'https://alibaba.github.io/weex/img/weex_logo_blue@3x.png',
      headUrl:'file:///storage/emulated/0/a98b31d0-8f70-4622-80c8-30d32bea9b0c.jpg',
      target: 'World'
    },
    methods: {
      update: function (e) {
        this.target = 'Weex';
      },
      modifyHead:function(e){
        var self = this;
        var event = weex.requireModule("event");
        event.requestModel("camera",function(e){
            if(e.result == 'WX_FAILED'){
                modal.toast({message: e.message});
            }else if(e.result = 'WX_SUCCESS'){
                var path = "file://"+e.message;
                console.log('=='+path)
                self.headUrl = path;
            }
        });
      }
    },
    components: {
      button: require('.././include/button.vue')
    }
  }


</script>