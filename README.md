# Monobank Booking System

This project built on Spring Boot using Spring Web, Spring Data JPA, and Spring HATEOAS modules.

## Project Description

Generally, it is a RESTful API for the booking system. This system composed of two sub-systems – a ticket management system and a payment system.
Abstractly, project is divided into three parts:

<img width="379" alt="Screenshot 2023-01-12 at 8 52 28 AM" src="https://user-images.githubusercontent.com/55809302/211999462-649a508a-19b7-426a-9960-9ef8257d8ded.png">

You can see three packages: ***paymentsystem***, ***ticketsystem***, and ***config*** (general configuration of the whole project).

## Requirements
- JDK 18+
- Spring 5+

## How to install, set up and run the project

1. Clone this GitHub repo using ```git clone https://github.com/Programmer00777/mono-booking-system.git```
2. Go to the root folder of the project and type ```./mvnw spring-boot:run```

You will see the following logs:

<img width="1610" alt="Screenshot 2023-01-12 at 9 03 59 AM" src="https://user-images.githubusercontent.com/55809302/211999740-1c768e6c-0438-44dc-96c2-d80efab3dc02.png">

So, you can see that by default, app is running on port `8080`.

Also, there are some important logs:
- Logs from `c.m.bookingsystem.config.LoadDatabase`:
```
2023-01-12T09:03:38.322+02:00  INFO 64009 --- [           main] c.m.bookingsystem.config.LoadDatabase    : Preloaded: Payment{id=ee1a37ac-7d6e-4456-9aba-6aa16ff155ed, fullName='Sergey Chernikov', amount=600.45, status=DONE}
2023-01-12T09:03:38.322+02:00  INFO 64009 --- [           main] c.m.bookingsystem.config.LoadDatabase    : Preloaded: Payment{id=d17e83c3-b2f7-4ccd-8283-bf9d6f0dbc77, fullName='John Doe', amount=400.0, status=FAILED}
2023-01-12T09:03:38.322+02:00  INFO 64009 --- [           main] c.m.bookingsystem.config.LoadDatabase    : Preloaded: Payment{id=d9216bf6-b520-4046-81b7-6f3d9e8791b9, fullName='Alex Brown', amount=500.0, status=NEW}
2023-01-12T09:03:38.324+02:00  INFO 64009 --- [           main] c.m.bookingsystem.config.LoadDatabase    : Preloaded: Trip{id=22faf411-6547-401e-9b67-5705ff3a98e9, from='Dnipro', to='Kharkiv', date=2022-01-14T11:00, price=600.45, available=40}
2023-01-12T09:03:38.324+02:00  INFO 64009 --- [           main] c.m.bookingsystem.config.LoadDatabase    : Preloaded: Trip{id=05b64e1b-8407-4b81-b8dc-07464ca93a94, from='Kiyv', to='Zhytomyr', date=2022-01-15T09:30, price=400.0, available=31}
2023-01-12T09:03:38.324+02:00  INFO 64009 --- [           main] c.m.bookingsystem.config.LoadDatabase    : Preloaded: Trip{id=fd2df281-b78d-4c4d-a6c5-110abd7c361d, from='Odesa', to='Mykolaiv', date=2022-02-04T15:45, price=500.0, available=37}
2023-01-12T09:03:38.324+02:00  INFO 64009 --- [           main] c.m.bookingsystem.config.LoadDatabase    : Preloaded: Trip{id=c752ba55-ab58-4d09-8d2c-56bb1773bca3, from='Poltava', to='Sumy', date=2022-02-04T12:15, price=375.0, available=0}
2023-01-12T09:03:38.325+02:00  INFO 64009 --- [           main] c.m.bookingsystem.config.LoadDatabase    : Preloaded: Ticket{id=93bba963-a48e-4993-ad0f-eba604a577bc, fullName='Sergey Chernikov', tripId=22faf411-6547-401e-9b67-5705ff3a98e9, paymentId=ee1a37ac-7d6e-4456-9aba-6aa16ff155ed}
2023-01-12T09:03:38.326+02:00  INFO 64009 --- [           main] c.m.bookingsystem.config.LoadDatabase    : Preloaded: Ticket{id=c9e15a74-a5cd-4973-9645-d8a09af14b84, fullName='John Doe', tripId=05b64e1b-8407-4b81-b8dc-07464ca93a94, paymentId=d17e83c3-b2f7-4ccd-8283-bf9d6f0dbc77}
2023-01-12T09:03:38.326+02:00  INFO 64009 --- [           main] c.m.bookingsystem.config.LoadDatabase    : Preloaded: Ticket{id=0ba4389a-4c73-43f2-9e5f-027f7ea3f4dd, fullName='Alex Brown', tripId=fd2df281-b78d-4c4d-a6c5-110abd7c361d, paymentId=d9216bf6-b520-4046-81b7-6f3d9e8791b9}
```
The application automatically preloads info the the database (H2): payments, trips, and tickets.

- Logs from `.s.PaymentStatusUpdateScheduledProcessor`:
```
2023-01-12T09:03:38.322+02:00  INFO 64009 --- [   scheduling-1] .s.PaymentStatusUpdateScheduledProcessor : Payment with ID ee1a37ac-7d6e-4456-9aba-6aa16ff155ed done.
2023-01-12T09:03:38.328+02:00  WARN 64009 --- [   scheduling-1] .s.PaymentStatusUpdateScheduledProcessor : Payment with ID d17e83c3-b2f7-4ccd-8283-bf9d6f0dbc77 failed.
2023-01-12T09:03:45.415+02:00  WARN 64009 --- [   scheduling-1] .s.PaymentStatusUpdateScheduledProcessor : Payment with ID d9216bf6-b520-4046-81b7-6f3d9e8791b9 failed.
```
There is scheduled processor that checks all the payments statuses (every 3.5s) and performs appropriate operations:
- if payment has the status value of "NEW", then we should process this payment until we receive "DONE" or "FAILED".
- if the status is "FAILED", then we should return the initial number of available tickets for that particular trip the ticket was attempted to buy for.
- if the status is "DONE", then we should not do anything.
- in both cases, once we received "DONE" or "FAILED", we set the flag **processed** to `true` for this payment and don't ask it anymore.

Basically, that's it. The app is running on port 8080 and ready for use.

## Endpoints
Ticket system:

- *GET /ticket/info/{ticketId}* get the info about ticket with ID `ticketId`
- *GET /ticket/info/all* get the info about all tickets in the system (if there are any)
- *POST /ticket/buy* buy a new ticket
- *PUT /available/update/{tripId}* update the number of available tickets for the trip with ID `tripId` in the case of failed payment

Payment system:
- *GET /payment/status/{paymentId}* get the payment status with payment ID `paymentId`
- *GET /payment/status/all* get statuses of all the payments in the system (if there are any)
- *POST /payment/create* create a new payment
- *PUT /payment/status/update/{paymentId}* update payment status for the payment with ID `paymentId` if the current state is "NEW"

## Usage example:
It's recommended to use Postman to test this system, since the Spring HATEOAS was used and it easy to navigate through endpoints by related links.

- Let's first go to the `http://localhost:8080/ticket/info/all` to fetch the info about all the tickets in the system:

We receive the response like this:
```
{
    "_embedded": {
        "ticketInfoDtoList": [
            {
                "ticketId": "93bba963-a48e-4993-ad0f-eba604a577bc",
                "status": "DONE",
                "tripInfo": {
                    "id": "22faf411-6547-401e-9b67-5705ff3a98e9",
                    "fromPlace": "Dnipro",
                    "toPlace": "Kharkiv",
                    "date": "2022-01-14T11:00:00",
                    "price": 600.45,
                    "available": 40
                },
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/ticket/info/93bba963-a48e-4993-ad0f-eba604a577bc"
                    },
                    "ticketInfoList": {
                        "href": "http://localhost:8080/ticket/info/all"
                    }
                }
            },
            {
                "ticketId": "c9e15a74-a5cd-4973-9645-d8a09af14b84",
                "status": "FAILED",
                "tripInfo": {
                    "id": "05b64e1b-8407-4b81-b8dc-07464ca93a94",
                    "fromPlace": "Kiyv",
                    "toPlace": "Zhytomyr",
                    "date": "2022-01-15T09:30:00",
                    "price": 400.0,
                    "available": 32
                },
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/ticket/info/c9e15a74-a5cd-4973-9645-d8a09af14b84"
                    },
                    "ticketInfoList": {
                        "href": "http://localhost:8080/ticket/info/all"
                    }
                }
            },
            {
                "ticketId": "0ba4389a-4c73-43f2-9e5f-027f7ea3f4dd",
                "status": "FAILED",
                "tripInfo": {
                    "id": "fd2df281-b78d-4c4d-a6c5-110abd7c361d",
                    "fromPlace": "Odesa",
                    "toPlace": "Mykolaiv",
                    "date": "2022-02-04T15:45:00",
                    "price": 500.0,
                    "available": 38
                },
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/ticket/info/0ba4389a-4c73-43f2-9e5f-027f7ea3f4dd"
                    },
                    "ticketInfoList": {
                        "href": "http://localhost:8080/ticket/info/all"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/ticket/info/all"
        }
    }
}
```
- Now, we can follow the **self** link of the first ticket to fetch the info only about this particular one:
```
{
    "ticketId": "93bba963-a48e-4993-ad0f-eba604a577bc",
    "status": "DONE",
    "tripInfo": {
        "id": "22faf411-6547-401e-9b67-5705ff3a98e9",
        "fromPlace": "Dnipro",
        "toPlace": "Kharkiv",
        "date": "2022-01-14T11:00:00",
        "price": 600.45,
        "available": 40
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/ticket/info/93bba963-a48e-4993-ad0f-eba604a577bc"
        },
        "ticketInfoList": {
            "href": "http://localhost:8080/ticket/info/all"
        }
    }
}
```

- Then, say we want to buy a new ticket from Kiyv to Zhytomyr (i.e. the preloaded trip with ID `05b64e1b-8407-4b81-b8dc-07464ca93a94`). For this reason, we want
to type the URL `http://localhost:8080/ticket/buy`, choose a *POST* request, go to the request body section, choose 'row' and 'JSON':

<img width="850" alt="Screenshot 2023-01-12 at 9 32 24 AM" src="https://user-images.githubusercontent.com/55809302/212005505-fcc913e4-79ad-47cc-bf1f-716ee5781a2a.png">

For example, enter the following requesy body:
```
{
    "fullName": "Test Test",
    "tripId": "05b64e1b-8407-4b81-b8dc-07464ca93a94"
}
```

Once you hit the "Send" button, you will receive the ticket ID. Also, once the button is clicked, quickly go to the `http://localhost:8080/ticket/info/all`
to see the list of all tickets altogether with a new one. The newly created ticket should have the `status` of "NEW":

<img width="809" alt="Screenshot 2023-01-12 at 9 39 42 AM" src="https://user-images.githubusercontent.com/55809302/212006654-7d4b7bbf-10a3-4cf4-8985-1ff3ab855ebd.png">

Now, let's go to the console and see the result of the payment execution:

<img width="1359" alt="Screenshot 2023-01-12 at 9 41 03 AM" src="https://user-images.githubusercontent.com/55809302/212006894-764ce50c-ca54-4cdc-a818-b8b11fa0d6d6.png">

We see that the payment was failed for some reason (actually this status was received randomly).

Note, that when the status of the payment was "NEW", the number of available tickets was equal to 31. The payment failed. What should we do? Return the initial
number of available tickets (i.e. 32). All you have to do is just send GET to `http://localhost:8080/ticket/info/all` one more time to see the updated info
about the ticket that was attempted to buy:

<img width="842" alt="Screenshot 2023-01-12 at 9 44 31 AM" src="https://user-images.githubusercontent.com/55809302/212007543-e22c5f05-c968-4550-8e1e-f2535bc8c4f5.png">

You see that now the status is `FAILED` and the number of available tickets is updated.

*What will happen if we try to buy the ticket for trip for which there no more tickets available?*

Let's try to buy a new ticket for the trip Poltava-Sumy (initially, it was preloaded on start up and the number of available tickets is set to 0).
Send a POST to `http://localhost:8080/ticket/buy` with the following request body:
```
{
    "fullName": "Failed Attemp ToBuyTicket",
    "tripId": "c752ba55-ab58-4d09-8d2c-56bb1773bca3"
}
```

We receive the message with the following contents:

<img width="670" alt="Screenshot 2023-01-12 at 9 48 17 AM" src="https://user-images.githubusercontent.com/55809302/212008311-e64f6da7-d0d7-4287-bc90-b8d98bd5e129.png">

So, the ticket wasn't even created and saved to the database. Everything is fine.

*Okay, let's test other endpoints (related to the payment system)*

- Say you want to see the info about the status of the last payment that was created (that is <img width="1359" alt="Screenshot 2023-01-12 at 9 41 03 AM" src="https://user-images.githubusercontent.com/55809302/212009181-0923b06c-10b2-4611-b580-6d0516956e4c.png">
).

Send GET to `http://localhost:8080/payment/status/f03ef881-8440-485e-b63b-a66fee478075`. Here is what a response we receive:
<img width="830" alt="Screenshot 2023-01-12 at 9 55 01 AM" src="https://user-images.githubusercontent.com/55809302/212009609-b916b3f7-d561-476b-90d8-3574603154dc.png">

Navigate to the list of all payments using "paymentStatusList" link:
```
{
    "_embedded": {
        "paymentStatusDtoList": [
            {
                "paymentId": "ee1a37ac-7d6e-4456-9aba-6aa16ff155ed",
                "status": "DONE",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/payment/status/ee1a37ac-7d6e-4456-9aba-6aa16ff155ed"
                    },
                    "paymentStatusList": {
                        "href": "http://localhost:8080/payment/status/all"
                    }
                }
            },
            {
                "paymentId": "d17e83c3-b2f7-4ccd-8283-bf9d6f0dbc77",
                "status": "FAILED",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/payment/status/d17e83c3-b2f7-4ccd-8283-bf9d6f0dbc77"
                    },
                    "paymentStatusList": {
                        "href": "http://localhost:8080/payment/status/all"
                    }
                }
            },
            {
                "paymentId": "d9216bf6-b520-4046-81b7-6f3d9e8791b9",
                "status": "FAILED",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/payment/status/d9216bf6-b520-4046-81b7-6f3d9e8791b9"
                    },
                    "paymentStatusList": {
                        "href": "http://localhost:8080/payment/status/all"
                    }
                }
            },
            {
                "paymentId": "73c54410-cdb7-4c87-8018-b9bdff227474",
                "status": "FAILED",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/payment/status/73c54410-cdb7-4c87-8018-b9bdff227474"
                    },
                    "paymentStatusList": {
                        "href": "http://localhost:8080/payment/status/all"
                    }
                }
            },
            {
                "paymentId": "f03ef881-8440-485e-b63b-a66fee478075",
                "status": "FAILED",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/payment/status/f03ef881-8440-485e-b63b-a66fee478075"
                    },
                    "paymentStatusList": {
                        "href": "http://localhost:8080/payment/status/all"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/payment/status/all"
        }
    }
}
```
We fetched the entire list of all payments that were created during the app run time.

**What if we try to directly create payment with wrong values (name or amount)?**

Send POST to `http://localhost:8080/payment/create` with this request body:
```
{
    "fullName": "Alex Alex",
    "amount": -250.35 // the amount is incorrect since it cannot be negative
}
```

<img width="842" alt="Screenshot 2023-01-12 at 10 03 26 AM" src="https://user-images.githubusercontent.com/55809302/212011261-7421f741-c5cd-4802-bf6b-a29d1a618c7b.png">

We received the response above with the status code of `409 - Conflict` and the cover message.

## Conclusion
That's pretty much it. It was a brief introduction to how to use this booking system.

If you have any questions, contact me on `sergey.chernikov.work@gmail.com`.

Thank you for your time!

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License

[MIT](https://choosealicense.com/licenses/mit/)
