<script setup>

</script>

<template>
  <div id="app" class="container mt-5">
    <h1>종목명: 아이언디바이스</h1>
    <h4>산업: 기타 수공업</h4>
    <p>대표: {{ representative }}</p>
    <p>매출: {{ revenue }}</p>
    <p>순이익: {{ netProfit }}</p>

    <hr>

    <h5>댓글</h5>
    <div class="comments mb-3">
      <div class="card mb-2" v-for="comment in ipoComments" :key="comment.id">
        <div class="card-body">
          <p class="card-text">{{ comment.ipoComments }}</p>
          <small class="text-muted">{{ comment.regDate }}</small>
        </div>
      </div>
    </div>

    <div class="comment-form">
      <textarea v-model="newComment" class="form-control mb-2" rows="3" placeholder="댓글을 입력하세요..."></textarea>
      <button class="btn btn-primary" @click="addComment">댓글 작성</button>
    </div>
  </div>
</template>

<style scoped>

</style>