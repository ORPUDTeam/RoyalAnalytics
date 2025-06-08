<template>
  <div class="admin-tournaments">
    <div class="d-flex justify-content-end mb-3">
      <base-button
          type="primary"
          @click="$router.push('/admin/tournaments/new')">
        <i class="ni ni-fat-add"></i> Создать турнир
      </base-button>
    </div>

    <div class="table-responsive">
      <table class="table align-items-center">
        <thead class="thead-light">
        <tr>
          <th>ID</th>
          <th>Название</th>
          <th>Тип</th>
          <th>Статус</th>
          <th>Дата начала</th>
          <th>Участники</th>
          <th>Действия</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="t in tournaments" :key="t.id">
          <td>{{ t.id }}</td>
          <td>{{ t.name }}</td>
          <td>
            <badge :type="t.type === 'OPEN' ? 'success' : 'info'">
              {{ t.type === 'OPEN' ? 'Открытый' : 'По приглашению' }}
            </badge>
          </td>
          <td>
            <badge :type="getStatusBadge(t.status)">
              {{ formatStatus(t.status) }}
            </badge>
          </td>
          <td>{{ formatDate(t.start_time) }}</td>
          <td>{{ t.participants_count }}/{{ t.capacity }}</td>
          <td class="td-actions">
            <base-button
                size="sm"
                type="default"
                @click="$emit('edit', t.id)">
              <i class="ni ni-ruler-pencil"></i>
            </base-button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    tournaments: { type: Array, required: true }
  },
  methods: {
    formatDate(date) {
      return new Date(date).toLocaleDateString('ru-RU');
    },
    formatStatus(status) {
      const statusMap = {
        'IN_PREPARATION': 'Подготовка',
        'ACTIVE': 'Активен',
        'COMPLETED': 'Завершен'
      };
      return statusMap[status] || status;
    },
    getStatusBadge(status) {
      const badgeMap = {
        'IN_PREPARATION': 'warning',
        'ACTIVE': 'success',
        'COMPLETED': 'default'
      };
      return badgeMap[status] || 'default';
    }
  }
}
</script>