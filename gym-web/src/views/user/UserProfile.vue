<script setup>
import {ref} from "vue";

const simpleAutocomplete = ref('');

const simpleQuerySearch = (queryString, cb) => {
  const fruits = [{value: 'Apple'}, {value: 'Banana'}, {value: 'Orange'}];
  const results = queryString ? fruits.filter(fruit => fruit.toLowerCase().includes(queryString.toLowerCase())) : fruits;
  cb(results);
}

const onSelect = item => {
  console.log('Selected:', item);
}

</script>

<template>
  <el-autocomplete
      v-model="simpleAutocomplete"
      :fetch-suggestions="simpleQuerySearch"
      placeholder="Type here"
      @select="onSelect"
  ></el-autocomplete>

</template>

<style scoped lang="stylus">

</style>
