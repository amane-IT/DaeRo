<template>
  <div>
    <h6>{{ expenses.expenses_name }} | {{expenses.expenses }}</h6>
  </div>
</template>

<script>
export default {
  name: 'expensesCard',
  props: {
    expenses: Object
  }
}
</script>

<style>

</style>