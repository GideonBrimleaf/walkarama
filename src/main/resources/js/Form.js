class Form {
  constructor(data) {
      this.originalData = JSON.parse(JSON.stringify(data))
      Object.assign(this, data)
      this.errors = {}
      this.submitted = false
      this.isWorking = false
  }

  data() {
      return Object.keys(this.originalData).reduce((data, attribute) => {
          data[attribute] = this[attribute]
          return data
      }, {})
  }

  post(endpoint) {
      this.submit(endpoint)
      .then(() => window.location.href = window.location.origin + endpoint)
  }

  patch(endpoint) {
      return this.submit(endpoint, 'patch')
  }

  delete(endpoint) {
      return this.submit(endpoint, 'delete')
  }

  submit(endpoint, requestType = 'post') {
      this.isWorking = true
      return axios[requestType](endpoint, this.data())
          .catch(this.onFail.bind(this))
          .then(this.onSuccess.bind(this))
          .then(() => this.isWorking = false)
  }

  onSuccess(response) {
      this.submitted = true
      this.errors = {}

      return response
  }

  onFail(error) {
      this.errors = error.response.data.errors
      this.submitted = false

      throw error
  }

  reset() {
      Object.assign(this, this.originalData)
  }
}

export default Form
