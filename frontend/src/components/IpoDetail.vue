<script setup>
  import {useIpoDetailStore} from "@/stores/IpoDetailStore.js";
  import { useRoute } from 'vue-router';
  import {onMounted, ref} from "vue";


  const route = useRoute();
  const ipoDetailStore = useIpoDetailStore();
  const ipoName = ref(route.query.ipoName); // ref로 감싸기// ref로 감싸기
  const response = ref(null); // response를 ref로 초기화
  const comments = ref('')

  const fetchIpoDetail = async () => {
    response.value = await ipoDetailStore.fetchIpoDetail(ipoName.value); // 데이터 가져오기
  };

  const regComments = async (comments) =>{
    console.log(comments + " 댓글이 왜 안들어올까 ? ")
    await ipoDetailStore.saveComments(ipoName.value,comments);
  }

  onMounted(fetchIpoDetail);
</script>

<template>
  <div id="app" class="container mt-5">
    <div class="card mb-4">
      <div class="card-body">
        <h1 class="card-title" >{{ response.data.ipoName }}</h1>
        <h4 class="card-subtitle mb-2 text-muted">{{ response.data.industry }}</h4>
        <p class="card-text"><strong>대표:</strong> {{ response.data.representative }}</p>
        <p class="card-text"><strong>매출:</strong> {{ response.data.revenue }}</p>
        <p class="card-text"><strong>순이익:</strong> {{ response.data.netProfit }}</p>
        <p class="card-text"><strong>경쟁률:</strong> {{ response.data.competitonRate }}</p>
        <p class="card-text"><strong>주간사:</strong> {{ response.data.securities }}</p>
        <p class="card-text"><strong>확정 공모가:</strong> {{ response.data.confirmPrice }}</p>
      </div>
    </div>

    <div class="comment-form mb-4">
      <textarea v-model="comments" class="form-control mb-2" rows="3" placeholder="댓글을 입력하세요..."></textarea>
      <button class="btn btn-primary" @click="regComments(comments)">댓글 작성</button>
    </div>

    <h5 class="mb-3">댓글</h5>
    <div class="comments mb-4">
      <div class="card mb-2" v-for="comment in response.data.ipoComments" :key="comment.id">
        <div class="card-body">
          <p class="card-text">{{ comment.ipoComments }}</p>
          <small class="text-muted">{{ comment.regDate }}</small>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.comments {
  max-height: 300px; /* 최대 높이 설정 */
  overflow-y: auto; /* 스크롤 가능 */
}

.card {
  border-radius: 0.5rem; /* 카드 모서리 둥글게 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 카드 그림자 추가 */
}

.card-body {
  padding: 1.5rem; /* 카드 내부 패딩 */
}

.card-title {
  font-size: 1.5rem; /* 제목 크기 조정 */
}

.card-subtitle {
  font-size: 1.2rem; /* 부제목 크기 조정 */
}

.comment-form textarea {
  border: 1px solid #ced4da; /* 기본 테두리 */
  border-radius: 0.25rem; /* 둥근 테두리 */
}

.comment-form button {
  width: 100%; /* 버튼 너비 100% */
}
</style>