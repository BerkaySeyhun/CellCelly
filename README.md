# CellCelly 

LinkedIn post can be reached from the link here ---------> [https://www.linkedin.com/feed/update/urn:li:activity:7096791741525090304/]

![Project_Logo](https://github.com/BerkaySeyhun/CellCelly/assets/60739501/00427551-c46e-41a1-8e35-e82b7339854d)


TelCo project during i2i 2023 internship.

Here is a table of the project

|       Name            |             Roles                 |
|-|-|  
| Ahmet Berkay Seyhun   | Project Manager                   |
| Efe Ertuğ Erdem       | DGW                               |
| Ali Geniş             | Kafka                             | 
| Mertkan İşcan         | OCS                               |
| Burak Celiloğlu       | Oracle DB                         |
| Emre Efe              | Test1 (OCS, DGW, SF, Kafka)       |
| Sena Akdoğan          | IVR-SMS                           |  
| Arda Adayener         | Test3 (IOS, Android, Web)         |
| Ali Enes Doğan        | IOS                               |
| Elifnur Kabalcı       | Android                           |
| Hatice Sudenas Çapacı | VoltDB                            |
| Aslıhan Altun         | Test2 (MW, VoltDB, Hazelcast)     |
| Ahmet Usta            | SF                                |
| Ramazan Fırat Akdağ   | MW                                |
| Okan Çezik            | Hazelcast                         |
| Erkam Doğrul          | Web                               |
| Muhammed Ali Fırat    | Analyst / Designer                |



The system shall:

- Provide user interfaces for mobile (Android/IOS), web, SMS, and IVR channels to allow users to register, login, view account details, and manage their account
- Integrate with various backend systems including MW, AOM, Kafka, SF, Oracle DB, OCS, and VoltDB to enable user account management, billing, and tariff functionalities
- Utilize Kafka and SF for data transmission between systems
- Leverage VoltDB to store and retrieve user account data including tariffs, usage, and remaining balances
- Use Oracle DB as the system of record for user, product, and billing data
- Employ OCS for real-time usage tracking, tariff management, and billing 
- Have test suites implemented covering functionality, performance, reliability, and data integrity for mobile apps, backends systems, and interfaces

The system architecture shall: 

- Consist of decoupled front-end channels, middleware, and backend data and processing systems
- Enable asynchronous near real-time data flow between systems using Kafka messaging
- Maintain data consistency across backends using Oracle DB as the source of truth

Key functional requirements include:

- User self sign-up and login across channels
- Real-time account usage tracking and balance deduction
- Configurable tariff plans and rate management
- Querying of remaining balances and usage
- Secure storage of user credentials and account data
- Reliable failover and recovery mechanisms
