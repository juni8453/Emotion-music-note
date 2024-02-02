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
import ErrorPage from "@/components/ErrorPage";

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
  {
    path: '/error',
    component: ErrorPage,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  if (to.path === '/' || to.path === '/login' || to.path === '/oauth/kakao' || to.path === '/error') {
    next();
  } else {
    // 홈, login, 카카오 OAuth 경로 제외 모든 라우터 이동 시 서버 상태 및 vuex 확인
    const apiServer = process.env.VUE_APP_API_SERVER;
    axios.get(`${apiServer}/api/check-server-state`)
    .then(response => {
      console.log(response.data);

      // 서버와 통신을 하고 있으나 사용자가 임의로 Vuex 를 지워버린 경우,
      if (localStorage.getItem('vuex') === null) {
        alert('로그인이 필요합니다.');
        window.location.href = '/';
      // 서버와 통신을 하고 있고 Vuex 값이 잘 존재하는 경우,
      } else {
        next();
      }

    }).catch((error) => {
      // 서버와 통신할 수 없는 경우 (서버 다운 등),
      if (error.code === 'ERR_NETWORK' || error.code === 'ERR_BAD_RESPONSE') {
        localStorage.removeItem('vuex');
        router.push('/error');

      // 서버와 통신을 하고 있으나 세션이 만료된 경우,
      } else {
        const errorStatus = error.response.data.code;
        if (errorStatus === 401) {
          localStorage.removeItem('vuex');
          alert(error.response.data.message);
          window.location.href = '/';
        }
      }
    });
  }
});

export default router;