from locust import HttpUser, task

class EcoTrekkerUser(HttpUser):

    @task
    def request(self):
        self.client.get("/v1/grid")
