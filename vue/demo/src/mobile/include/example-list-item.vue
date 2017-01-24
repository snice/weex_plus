<template>
  <list-item v-on:click="redirect">
    <text class="item-txt">{{title}}</text>
  </list-item>
</template>

<style>
  .item-txt {
    font-size: 48px;
    color: #555;
  }
</style>

<script>
  module.exports = {
    props: {
      title: { default: '456' },
      url: { default: '' }
    },
    components: {
      listItem: require('./list-item.vue')
    },
    methods: {
      redirect: function() {
        weex.requireModule("event").openURL(this.url);
      }
    }
  }
</script>
