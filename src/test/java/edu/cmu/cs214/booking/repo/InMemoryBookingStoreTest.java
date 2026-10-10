package edu.cmu.cs214.booking.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.cmu.cs214.booking.domain.Booking;
import edu.cmu.cs214.booking.domain.Room;
import edu.cmu.cs214.booking.domain.TimeInterval;
import edu.cmu.cs214.booking.domain.User;
import org.junit.jupiter.api.Test;

class InMemoryBookingStoreTest {

    private final Room roomA = new Room("A", "Alpha", 10);
    private final Room roomB = new Room("B", "Beta", 4);
    private final User alice = new User("u1", "Alice");
    private final User bob = new User("u2", "Bob");

    @Test
    void bookingsForRoomReturnsOnlyThatRoomsBookings() {
        InMemoryBookingStore store = new InMemoryBookingStore();
        store.addBooking(new Booking("b1", roomA, alice, new TimeInterval(600, 660)));
        store.addBooking(new Booking("b2", roomB, bob, new TimeInterval(600, 660)));

        // Regression: bookingsForRoom must filter by room, otherwise different
        // rooms booked in the same slot would be reported as conflicts.
        assertEquals(1, store.bookingsForRoom(roomA).size());
        assertEquals("b1", store.bookingsForRoom(roomA).get(0).id());
        assertEquals(1, store.bookingsForRoom(roomB).size());
        assertEquals("b2", store.bookingsForRoom(roomB).get(0).id());
    }
}
