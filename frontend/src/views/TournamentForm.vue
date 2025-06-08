<template>
  <div class="tournament-form">
    <card>
      <template #header>
        <h3>{{ isEdit ? 'Редактирование турнира' : 'Создание турнира' }}</h3>
      </template>

      <validation-observer v-slot="{ handleSubmit }">
        <form @submit.prevent="handleSubmit(submit)">
          <div class="row">
            <div class="col-md-6">
              <validation-provider rules="required|min:3" v-slot="{ errors }">
                <base-input
                    label="Название турнира*"
                    v-model="form.name"
                    :error="errors[0]" />
              </validation-provider>
            </div>
            <div class="col-md-6">
              <validation-provider rules="required" v-slot="{ errors }">
                <base-select
                    label="Тип турнира*"
                    :options="typeOptions"
                    v-model="form.type"
                    :error="errors[0]" />
              </validation-provider>
            </div>
          </div>

          <div class="row mt-3">
            <div class="col-md-6">
              <validation-provider rules="required|numeric" v-slot="{ errors }">
                <base-input
                    label="Макс. участников*"
                    type="number"
                    v-model="form.maxCapacity"
                    :error="errors[0]" />
              </validation-provider>
            </div>
            <div class="col-md-6">
              <base-input
                  label="Уровень ограничения"
                  type="number"
                  v-model="form.levelCap" />
            </div>
          </div>

          <div class="row mt-3">
            <div class="col-md-12">
              <base-input
                  label="Описание"
                  type="textarea"
                  v-model="form.description" />
            </div>
          </div>

          <div class="form-actions mt-4">
            <base-button
                type="primary"
                native-type="submit"
                :loading="loading">
              {{ isEdit ? 'Обновить' : 'Создать' }}
            </base-button>
            <base-button
                type="default"
                @click="cancel">
              Отмена
            </base-button>
          </div>
        </form>
      </validation-observer>
    </card>
  </div>
</template>

<script>
export default {
  props: {
    isEdit: { type: Boolean, default: false },
    tournamentData: { type: Object, default: null }
  },
  data() {
    return {
      form: {
        name: '',
        type: 'OPEN',
        description: '',
        maxCapacity: 100,
        levelCap: null
      },
      typeOptions: [
        { value: 'OPEN', label: 'Открытый' },
        { value: 'INVITE_ONLY', label: 'По приглашению' }
      ],
      loading: false
    }
  },
  created() {
    if (this.isEdit && this.tournamentData) {
      this.form = { ...this.tournamentData };
    }
  },
  methods: {
    async submit() {
      this.loading = true;
      try {
        const url = this.isEdit
            ? `/tournaments/${this.tournamentData.id}`
            : '/tournaments/new';

        const method = this.isEdit ? 'patch' : 'post';

        await this.$axios[method](url, this.form);
        this.$notify({
          type: 'success',
          message: `Турнир успешно ${this.isEdit ? 'обновлён' : 'создан'}`
        });
        this.$router.push('/admin/panel');
      } catch (error) {
        this.$notify({
          type: 'danger',
          message: error.response?.data?.message || 'Произошла ошибка'
        });
      } finally {
        this.loading = false;
      }
    },
    cancel() {
      this.$router.push('/admin/panel');
    }
  }
}
</script>