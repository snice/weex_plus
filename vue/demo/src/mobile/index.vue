<template>
  <example-list :items="items" dir="dist"></example-list>
</template>

<script>
  module.exports = {
    data: function () {
      return {
        items: [
          // common
          {name: 'syntax/piano', title: 'Piano'},
          {name: 'syntax/hello-world', title: 'Hello World'},
          {name: 'style/index', title: 'Common Style'},
          {name: 'animation', title: 'Animation'},

          // component
          {name: 'components/text', title: 'Text'},
          {name: 'components/image', title: 'Image'},
          {name: 'components/input', title: 'Input'},
          {name: 'components/scroller', title: 'Scroller'},
          {name: 'components/list', title: 'List'},
          {name: 'components/slider', title: 'Slider'},
          {name: 'components/a', title: 'A'},
          {name: 'components/video', title: 'Video'},
          {name: 'components/countdown', title: 'Countdown'},
          {name: 'components/marquee', title: 'Marquee'},
          {name: 'components/web', title: 'Web'},
          {name: 'components/navigator', title: 'Navigator'},
          {name: 'components/tabbar', title: 'Tabbar'},

          // module
          {name: 'modules/instance-api', title: 'Instance API'},
          {name: 'modules/modal', title: 'Modal'},
          {name: 'modules/stream', title: 'Stream'},
          {name: 'modules/storage',title:'Storage'},
          // {name: 'module/clipboard', title: 'Clipboard'}, // 0.8 , developing

          // showcase
          {name: 'showcase/progress', title: 'Progress Bar'},
          {name: 'showcase/itemlist', title: 'List (Advanced)'},
          {name: 'showcase/calculator', title: 'Calculator'},
          // {name: 'showcase/minesweeper', title: 'Minesweeper'},
          // {name: 'showcase/ui', title: 'UI Gallery'},
          // {name: 'showcase/dropdown/dropdown-demo', title: 'Dropdown'}
        ]
      }
    },
    components: {
      exampleList: require('./include/example-list.vue')
    }
  }
</script>
