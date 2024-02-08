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
  if (to.path === '/' || to.path === '/login' || to.path === '/logout' || to.path === '/oauth/kakao' || to.path === '/error') {
    next();
  }

  else {
    const apiServer = process.env.VUE_APP_API_SERVER;
    axios.defaults.withCredentials = true;
    axios.get(`${apiServer}/api/check-server-state`)
    .then(response => {
      // 1. 서버와 통신 가능한 상태
      console.log('Router 에서 서버 체크')
      console.log(response.data);

      // 2. 세션 체크
      axios.get(`${apiServer}/api/check-session`)
      .catch((error => {
        const errorStatus = error.response.data.code;

        // Interceptor preHandler()
        if (errorStatus === 401) {
          localStorage.removeItem('vuex');
          alert(error.response.data.message);
          window.location.href = '/';
        }
      }))

      // 3. 문제 없다면 이동
      next();

    }).catch(() => {
      // 2. 서버와 통신이 불가능한 상태
      // 에러 페이지 출력
      router.push('/error');
    })
  }
})

export default router;
