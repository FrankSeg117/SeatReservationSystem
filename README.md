## Main course Data Structures Project

Welcome to our humble repository. Here, you will see the source code for our course project for our data structures class.

## What is this project?

In this project, we implemented a system for seat reservation in a baseball stadium. This system manages the available seats, client reservations, and a waitlist for a section that may be full. The system allows a user to reserve an available seat, cancel reservations, see the availability of free seats, undo the recent transactions, and view a transaction history. 

### Key Details

### Stadium Sections and Pricing

- **Field Level**: Premium seats ($300), Capacity: 500  
- **Main Level**: Good view seats ($120), Capacity: 1,000  
- **Grandstand Level**: Upper-level seats ($45), Capacity: 2,000  

### Core Features

- **Seat Reservation**: Allows customers to book available seats.  
- **Waitlist Management**: Adds customers to a waitlist for full levels.  
- **Cancel Reservation**: Frees up seats, allowing the next person on the waitlist to reserve them.  
- **Availability Check**: Shows available sections and allows customer selection.  

### Technical Implementation

Data Structures:
- **Set**: For available seats, and bought seats by level
- **LinkedList**: For reservation history.  
- **HashMap**: To associate customers with their seat and other minor implementations  
- **Stack**: To support an undo feature for reservations and cancellations.  
- **Queue**: To manage the waitlist for full levels.  

### Summary of Logic Implementation:

This project works as a basis of many menus that call others depending on the inputs of the operator.
Whenever there will be a reservation, the app asks the operator the details of the client and which seats would they like, 
and proceed to move seats from a set of free seats to the set of occupied seats and one of many dictionaries for the client 
and Array List of Seats. Cancelation does a similar operation but on reverse. It takes an identifier to find a client in the
system (while making sure it exists) and then finds which seats that client has reserved, once we know we give the option
to select which seats to unreserve moving the seats back to the original set where the free seats are stored.
Undo takes both implementations and does the opposite the operation stored.



## Members with their contact information:

| Name               | Position                     | Email                        |
|--------------------|------------------------------|------------------------------|
| Tomás Gómez        | Software Engineering Student | tomas.gomez1@upr.edu         |
| Jonathan González  | Computer Engineering Student | jonathan.gonzalez57@upr.edu  |
| Frank Seguí        | Computer Engineering Student | frank.segui1@upr.edu         |

