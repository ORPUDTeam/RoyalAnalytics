<template>
  <div class="admin-panel">
    <card>
      <template #header>
        <h3>Панель администратора</h3>
        <p class="text-muted">Управление пользователями и системой</p>
      </template>

      <tabs>
        <tab-pane label="Пользователи">
          <admin-users-table
              :users="users"
              @block="handleBlock"
              @unblock="handleUnblock" />
        </tab-pane>
        <tab-pane label="Турниры">
          <admin-tournaments-table
              :tournaments="tournaments"
              @edit="editTournament" />
        </tab-pane>
      </tabs>
    </card>
  </div>
</template>

<script>
import { fetchAllUsers, fetchAdminTournaments } from '@/api/admin';

export default {
  data() {
    return {
      users: [],
      tournaments: [],
      loading: true
    }
  },
  async mounted() {
    await this.loadData();
  },
  methods: {
    async loadData() {
      try {
        const [users, tournaments] = await Promise.all([
          fetchAllUsers(),
          fetchAdminTournaments()
        ]);
        this.users = users;
        this.tournaments = tournaments;
      } catch (error) {
        this.handleError(error, 'Ошибка загрузки данных');
      } finally {
        this.loading = false;
      }
    },
    async handleBlock(userId) {
      try {
        await this.$axios.patch(`/admin/block/${userId}`);
        this.updateUserStatus(userId, 'blocked');
        this.showSuccess('Пользователь заблокирован');
      } catch (error) {
        this.handleError(error, 'Ошибка блокировки');
      }
    },
    async handleUnblock(userId) {
      try {
        await this.$axios.patch(`/admin/unblock/${userId}`);
        this.updateUserStatus(userId, 'active');
        this.showSuccess('Пользователь разблокирован');
      } catch (error) {
        this.handleError(error, 'Ошибка разблокировки');
      }
    },
    editTournament(id) {
      this.$router.push(`/admin/tournaments/${id}/edit`);
    },
    updateUserStatus(id, status) {
      this.users = this.users.map(u =>
          u.id === id ? { ...u, status } : u
      );
    },
    handleError(error, defaultMsg) {
      const message = error.response?.data?.message || defaultMsg;
      this.$notify({ type: 'danger', message });
    },
    showSuccess(message) {
      this.$notify({ type: 'success', message });
    }
  }
}
</script>