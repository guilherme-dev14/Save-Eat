<template>
    <div class="layout">
        <aside class="drawer">
            <h2>Save&Eat</h2>
            <div class="avatar">
                <router-link :to="`/${tipoUsuario}/perfil`">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="35" height="32" fill="#6d7072">
                        <path
                            d="M12,2A10,10 0 0,0 2,12A10,10 0 0,0 12,22A10,10 0 0,0 22,12A10,10 0 0,0 12,2M7.07,18.28C7.5,17.38 10.12,16.5 12,16.5C13.88,16.5 16.5,17.38 16.93,18.28C15.57,19.36 13.86,20 12,20C10.14,20 8.43,19.36 7.07,18.28M18.36,16.83C16.93,15.09 13.46,14.5 12,14.5C10.54,14.5 7.07,15.09 5.64,16.83C4.62,15.5 4,13.82 4,12C4,7.59 7.59,4 12,4C16.41,4 20,7.59 20,12C20,13.82 19.38,15.5 18.36,16.83M12,6C10.06,6 8.5,7.56 8.5,9.5C8.5,11.44 10.06,13 12,13C13.94,13 15.5,11.44 15.5,9.5C15.5,7.56 13.94,6 12,6M12,11A1.5,1.5 0 0,1 10.5,9.5A1.5,1.5 0 0,1 12,8A1.5,1.5 0 0,1 13.5,9.5A1.5,1.5 0 0,1 12,11Z" />
                    </svg>
                </router-link>
                <router-link v-if="tipoUsuario === 'cliente'" :to="`/${tipoUsuario}/notificacoes`">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="3 0 24 24" width="24" height="32" fill="#6d7072">

                        <path
                            d="M10 21H14C14 22.1 13.1 23 12 23S10 22.1 10 21M21 19V20H3V19L5 17V11C5 7.9 7 5.2 10 4.3V4C10 2.9 10.9 2 12 2S14 2.9 14 4V4.3C17 5.2 19 7.9 19 11V17L21 19M17 11C17 8.2 14.8 6 12 6S7 8.2 7 11V18H17V11Z" />
                    </svg>
                </router-link>

            </div>

            <ul>
                <li><router-link :to="`/${tipoUsuario}`" exact class="menu-link">
                        <span class="icon-text">
                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="25" height="32"
                                fill="rgba(29, 29, 29, 0.822)">
                                <path
                                    d="M12 5.69L17 10.19V18H15V12H9V18H7V10.19L12 5.69M12 3L2 12H5V20H11V14H13V20H19V12H22" />
                            </svg>
                            <span class="label">Página Inicial</span>
                        </span>
                    </router-link></li>
                <li><router-link :to="`/${tipoUsuario}/produtos`" exact class="menu-link last-link">
                        <span class="icon-text">
                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="25" height="32"
                                fill="rgba(29, 29, 29, 0.822)">
                                <path
                                    d="M21.41 11.58L12.41 2.58A2 2 0 0 0 11 2H4A2 2 0 0 0 2 4V11A2 2 0 0 0 2.59 12.42L11.59 21.42A2 2 0 0 0 13 22A2 2 0 0 0 14.41 21.41L21.41 14.41A2 2 0 0 0 22 13A2 2 0 0 0 21.41 11.58M13 20L4 11V4H11L20 13M6.5 5A1.5 1.5 0 1 1 5 6.5A1.5 1.5 0 0 1 6.5 5Z" />
                            </svg>
                            <span>Produtos</span>
                        </span>
                    </router-link></li>
            </ul>
            <div class="logout-container">
                <button class="menu-link logout-link" @click="logout">
                    <span class="icon-text">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="35" height="32"
                            fill="#6d7072">
                            <path
                                d="M16 13v-2H7V8l-5 4 5 4v-3zM20 3h-8v2h8v14h-8v2h8c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2z" />
                        </svg>
                        <span>Sair</span>
                    </span>
                </button>
            </div>
        </aside>
        <main class="content">
            <slot />
        </main>
    </div>
</template>

<script lang="ts">
import { logout } from '@/services/authUtils'
import Vue from 'vue'

export default Vue.extend({
    name: 'NavigationDrawer',
    computed: {
        tipoUsuario(): string {
            return this.$route.path.includes('/empresa') ? 'empresa' : 'cliente'
        }
    },
    methods: {
        logout() {
            logout()
            this.$router.push('/')
        }
    }
})
</script>


<style scoped>
.layout {
    display: flex;
    min-height: 100vh;
}

.drawer a {
    text-decoration: none;
    display: block;
    padding: 10px 12px;
    border-radius: 6px;
    transition: background-color 0.2s ease;
}

.drawer a:hover {
    background-color: #dddddd;
}

.drawer a.router-link-active,
.drawer a.router-link-exact-active {
    background-color: #dddddd;
}

.drawer h2 {
    margin-bottom: 10px;
    font-size: 24px;
    color: #6d7072;
}

.avatar {
    display: flex;
    justify-content: center;
    margin-bottom: 10px;
    align-items: center;
}

.drawer ul {
    list-style: none;
    padding: 0;
    margin: 0;

}

.drawer a {
    color: rgba(29, 29, 29, 0.822);
    text-decoration: none;
    font-weight: bold;
}


.drawer {
    width: 220px;
    background-color: #ffffff;
    color: rgba(29, 29, 29, 0.822);
    padding: 20px;
    box-shadow: 2px 0 5px rgba(0, 0, 0, 0.05);
    border-right: 1px solid #e0e0e0;
    position: relative;
}

.content {
    flex: 1;
    padding: 30px;
    background-color: #ffffff;
}

.menu-link {
    display: flex;
    align-items: center;
}

.icon-text {
    display: flex;
    align-items: center;
    gap: 6px;
}

.icon-text svg {
    flex-shrink: 0;
}

.label {
    font-size: 16px;
}

.last-link {
    border-bottom: 1px solid #bbbdbe;
}

.logout-container {
    position: absolute;
    bottom: 20px;
    left: 0;
    width: 100%;
    text-align: center;
    /* para centralizar */
}

.logout-link {
    background: none;
    border: none;
    text-align: center;
    padding: 8px 12px;
    cursor: pointer;
    font-weight: bold;
    border-radius: 6px;
    transition: background-color 0.2s ease;
    display: inline-flex;
    align-items: center;
    gap: 6px;
}

.logout-link:hover {
    background-color: #dddddd;
}
</style>
