from locust import HttpUser, between, constant_throughput, task

car_request = {
    "gamification" : False,
    "routes" : [
        {
            "steps" : [
                {
                    "vehicle" : "/car",
                    "distance" : 3000.0,
                }
            ]
        }
    ]
}

bike_request = {
    "gamification" : False,
    "routes" : [
        {
            "steps" : [
                {
                    "vehicle" : "/car",
                    "distance" : 3000.0,
                },
                {
                    "vehicle" : "/bike/e-bike",
                    "distance" : 12000.0,
                }
            ]
        }
    ]
}

metro_request = {
    "gamification" : False,
    "routes" : [
        {
            "steps" : [
                {
                    "vehicle" : "/car",
                    "distance" : 3000.0,
                },
                {
                    "vehicle" : "/bike/e-bike",
                    "distance" : 12000.0,
                },
                {
                    "vehicle" : "/metro",
                    "distance" : 6000.0,
                }
            ]
        }
    ]
}

uni_request = {
    "gamification" : False,
    "routes" : [
        {
            "steps" : [
                {
                    "vehicle" : "/bus",
                    "line" : "169",
                    "start" : "Müggelheim/Dorf (Berlin)",
                    "end" : "S Köpenick (Berlin)",
                },
                {
                    "vehicle" : "/train",
                    "line" : "S3",
                    "start" : "S Köpenick (Berlin)",
                    "end" : "S Tiergarten (Berlin)",
                }
            ]
        }
    ]
}

class EcoTrekkerUser(HttpUser):
    @task
    def car(self):
        self.client.post("/v1/calc/co2",
                         json=car_request,
                         name="Single Car Step")
        
    @task
    def bike(self):
        self.client.post("/v1/calc/co2",
                         json=bike_request,
                         name="Single Bike Step")
        
    @task
    def metro(self):
        self.client.post("/v1/calc/co2",
                         json=metro_request,
                         name="Single U-Bahn Step")
        
    @task(2)
    def uni(self):
        self.client.post("/v1/calc/co2",
                         json=uni_request,
                         name="University Route")
        