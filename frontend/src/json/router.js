import {createRouter, createWebHistory} from "vue-router";
import UserLogin from "@/components/UserLogin";
import KakaoJoin from "@/components/KakaoJoin";
import HomePage from "@/components/HomePage";
import SearchSaveSong from "@/components/SearchSaveSong";
import NoteSave from "@/components/NoteSave";
import NoteDetail from "@/components/NoteDetail";
import NoteList from "@/components/NoteList";
import NoteUpdate from "@/components/NoteUpdate";
import axios from 'axios';
import SearchUpdateSong from "@/components/SearchUpdateSong";
import KakaoOut from "@/components/KakaoOut";

const routes = [
  {
    path: '/',
    component: HomePage,
  },
  {
    path: '/login',
    component: UserLogin,
  },
  {
    path: '/logout',
    component: KakaoOut,
  },
  {
    // kakao redirect uri (인가 코드 받는 즉시 컴포넌트 생성)
    path: '/oauth/kakao',
    component: KakaoJoin,
  },
  {
    path: "/notes",
    component: NoteList,
  },
  {
    path: "/note/detail/:noteId",
    component: NoteDetail,
  },
  {
    path: '/note/update/:noteId',
    component: NoteUpdate,
  },
  {
    path: '/note/new',
    component: NoteSave,
  },
  {
    path: '/song/save/search',
    component: SearchSaveSong,
  },
  {
    path: '/song/update/search',
    component: SearchUpdateSong,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  if (to.path === '/' || to.path === '/login' || to.path === '/oauth/kakao') {
    next();
  } else {
    // 홈, login, 카카오 OAuth 경로 제외 모든 라우터 이동 시 서버 상태 및 vuex 확인
    axios.get('http://localhost:8080/check-server-state')
    .then(response => {
      console.log(response.data);
      if (localStorage.getItem('vuex') === null) {
        alert('로그인이 필요합니다.');
        window.location.href = '/';
      } else {
        next();
      }
    }).catch(error => {
      console.log(error);
      localStorage.removeItem('vuex');
      alert('로그인이 필요합니다.');
      window.location.href = '/';
    });
  }
});

export default router;