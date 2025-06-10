<template>
  <div class="auth-page">
    <div class="container">
      <div class="row justify-content-center">
        <div class="col-md-6">
          <card class="mt-5">
            <h2 slot="header" class="text-center">Регистрация</h2>

            <validation-observer v-slot="{ handleSubmit }">
              <form @submit.prevent="handleSubmit(register)">
                <!-- Логин -->
                <validation-provider name="Логин" rules="required|min:3" v-slot="{ errors }">
                  <base-input
                      label="Логин"
                      v-model="form.username"
                      :error="errors[0]"
                      placeholder="Введите ваш логин">
                  </base-input>
                </validation-provider>

                <!-- Email -->
                <validation-provider name="Email" rules="required|email" v-slot="{ errors }">
                  <base-input
                      label="Email"
                      type="email"
                      v-model="form.email"
                      :error="errors[0]"
                      placeholder="example@mail.com">
                  </base-input>
                </validation-provider>

                <!-- Player Tag -->
                <validation-provider name="Player Tag" rules="required|min:5" v-slot="{ errors }">
                  <base-input
                      label="Player Tag"
                      v-model="form.player_tag"
                      :error="errors[0]"
                      placeholder="#ABCDEF">
                    <template #addon-left>
                      <span class="input-group-text">#</span>
                    </template>
                  </base-input>
                </validation-provider>

                <!-- Пароль -->
                <validation-provider name="Пароль" rules="required|min:6" v-slot="{ errors }">
                  <base-input
                      label="Пароль"
                      type="password"
                      v-model="form.password"
                      :error="errors[0]"
                      placeholder="Не менее 6 символов">
                  </base-input>
                </validation-provider>

                <!-- Подтверждение пароля -->
                <validation-provider name="Подтверждение" rules="required|confirmed:Пароль" v-slot="{ errors }">
                  <base-input
                      label="Подтвердите пароль"
                      type="password"
                      v-model="form.confirm_password"
                      :error="errors[0]"
                      placeholder="Повторите пароль">
                  </base-input>
                </validation-provider>

                <!-- Кнопка отправки -->
                <div class="text-center mt-4">
                  <base-button type="primary" native-type="submit" :loading="loading">
                    Зарегистрироваться
                  </base-button>
                </div>
              </form>
            </validation-observer>

            <div class="text-center mt-3">
              <router-link to="/login">Уже есть аккаунт? Войти</router-link>
            </div>
          </card>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {
        username: '',
        email: '',
        player_tag: '',
        password: '',
        confirm_password: ''
      },
      loading: false
    }
  },
  methods: {
    async register() {
      try {
        this.loading = true;
        const response = await this.$axios.post('/registration', {
          name: this.form.username,
          email: this.form.email,
          player_tag: this.form.player_tag,
          password: this.form.password,
          confirm_password: this.form.confirm_password
        });

        if (response.status === 302) {
          this.$router.push('/login');
          this.$notify({
            type: 'success',
            message: 'Регистрация прошла успешно!'
          });
        }
      } catch (error) {
        let message = 'Ошибка регистрации';
        if (error.response && error.response.status === 409) {
          message = 'Пользователь с таким email или player tag уже существует';
        }
        this.$notify({
          type: 'danger',
          message: message
        });
      } finally {
        this.loading = false;
      }
    }
  }
}
</script>

<style scoped>
.auth-page {
  min-height: 100vh;
  background: #f8f9fe;
  padding-top: 2rem;
}
.input-group-text {
  background-color: #f8f9fe;
}
</style>