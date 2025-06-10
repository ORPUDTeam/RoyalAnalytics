import { createRouter, createWebHistory } from "vue-router";
import Dashboard from "../views/Dashboard.vue";
import Profile from "../views/Profile.vue";
import Signup from "../views/Signup.vue";
import Signin from "../views/Signin.vue";
import Cards from "../views/Cards.vue";
import CardDetail from "../views/CardDetail.vue";
import Players from "../views/PlayerProfile.vue";
import Decks from "../views/Decks.vue";
import Tournaments from "../views/Tornaments.vue";
import MyTournaments from "../views/MyTournaments.vue";
import EditDeck from "../views/EditDeck.vue";
import GenerateDeck from "../views/GenerateDeck.vue";
import NewDeck from "@/views/NewDeck.vue";

// Определение маршрутов
const routes = [
  {
    path: "/",
    name: "/",
    redirect: "/dashboard-default",
  },
  {
    path: "/dashboard-default",
    name: "Dashboard",
    component: Dashboard,
  },
  {
    path: "/profile",
    name: "Profile",
    component: Profile,
  },
  {
    path: "/signin",
    name: "Signin",
    component: Signin,
  },
  {
    path: "/signup",
    name: "Signup",
    component: Signup,
  },
  {
    path: "/players/:player_tag",
    name: "PlayerProfile",
    component: Players,
  },
  {
    path: "/decks",
    name: "Decks",
    component: Decks,
  },
  {
    path: "/decks/new",
    name: "NewDeck",
    component: NewDeck,
  },
  {
    path: "/decks/:deck_id/edit",
    name: "EditDeck",
    component: EditDeck,
  },
  {
    path: "/decks/generate",
    name: "GenerateDeck",
    component: GenerateDeck,
  },
  {
    path: "/tournaments",
    name: "Tournaments",
    component: Tournaments,
  },
  {
    path: "/my-tournaments",
    name: "MyTournaments",
    component: MyTournaments,
  },
  {
    path: "/cards",
    name: "Cards",
    component: Cards,
  },
  {
    path: "/cards/:card_id",
    name: "CardDetail",
    component: CardDetail,
  },
];

// Создание роутера
const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
  linkActiveClass: "active",
});

export default router;
