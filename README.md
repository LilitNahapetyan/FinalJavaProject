# Advanced Hotel Room Booking System

The **Advanced Hotel Room Booking System** is a console-based application designed to manage bookings of hotel rooms efficiently. This system provides a user-friendly interface for hotel operators to register rooms, customers, make bookings, generate bills, create reports, and save the system's state for future use. It is built with a focus on robustness, error handling, and ease of use.

## Features

### Room Registration

- The system allows operators to add various types of rooms to the hotel, including Single, Double, and Deluxe rooms.
- Each room is associated with a unique ID and a specific room type.
- Room types and their details:
  - Single Room: Single bed, bathroom, TV, closet. Price: $20 per day.
  - Double Room: Double bed, bathroom, TV, closet. Price: $35 per day.
  - Deluxe Room: Minibar, bathtub, king-size bed, sitting area. Price: $55 per day.

### Customer Registration

- Operators can add customer details, including their name and contact email, for future bookings.

### Room Booking

- Customers can book rooms for specific periods.
- The system ensures that a room cannot be booked by multiple customers for the same period.
- Booking information is stored in both the room's booking history and the customer's booking history.

### Bill Generation & Saving

- Detailed bills are generated for each booking, including room charges, taxes (20%), and service fees (10% extra).
- Bills can be viewed in the console or saved as text files.
- Bill details include room ID and type, customer's name and contact email, booking period, booking price, taxes, service fees, and the total amount.

### Reports

- The system allows users to generate reports based on the history of bookings for specific rooms and upcoming bookings.
- Reports can be saved as text files for future reference.

### Saving & Loading

- The system can save its entire state to the file system, making it possible to resume operations from where they left off.
- The saved state can be loaded back into the system, ensuring data persistence.

## How to Use

The **Advanced Hotel Room Booking System** can be operated in the console with the following tasks:

1. Add a room: Register new rooms with their unique IDs and types.
2. Add a customer: Register customer details for future bookings.
3. Book a room for a customer: Choose a room type and booking period for a customer. The system will generate and display the bill.
4. Save the state: Save the current state of the system to the file system by specifying the file's full path.
5. Load the state: Load a previously saved state of the system from a file.
6. Make a report for a specific room: Generate a report for a specific room, including booking history, and save it as a text file by specifying the file's full path.

The system is designed to handle exceptions gracefully and ensure a smooth user experience.

## Evaluation Criteria

The project will be evaluated based on the following criteria:

- Correct and complete implementation of features.
- Code quality, including adherence to naming conventions and the use of comments.
- Robust error handling and input validation.
- Effective use of data structures and algorithms.
- User interface design and usability in the console.
- Overall system design and organization.

