<template>
  <div>
    <h1>您好，欢迎进入控台！</h1>
  </div>
</template>

<script>
    export default {
        name: 'welcome',
        mounted: function(){
            //sidebar激活样式 方法1
            //this.$parent.activeSidebar("welcome-sidebar");
        },
        methods:{
        }
    }
</script>