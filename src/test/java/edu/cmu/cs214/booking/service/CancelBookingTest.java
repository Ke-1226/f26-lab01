package edu.cmu.cs214.booking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.cmu.cs214.booking.domain.Room;
import edu.cmu.cs214.booking.domain.TimeInterval;
import edu.cmu.cs214.booking.domain.User;
import edu.cmu.cs214.booking.repo.InMemoryBookingStore;
import org.junit.jupiter.api.Test;

class CancelBookingTest {

    private final Room roomA = new Room("A", "Alpha", 10);
    private final Room roomB = new Room("B", "Beta", 4);
    private final User alice = new User("u1", "Alice");
    private final User bob = new User("u2", "Bob");
    private final User carol = new User("u3", "Carol");

    private BookingService newService() {
        return new BookingService(new InMemoryBookingStore());
    }

    private static TimeInterval at(int start, int end) {
        return new TimeInterval(start, end);
    }

    @Test
    void cancelRemovesConfirmedBooking() {
        BookingService svc = newService();
        BookingResult.Confirmed r =
                (BookingResult.Confirmed) svc.book(roomA, alice, at(600, 660));

        svc.cancelBooking(r.booking().id());

        assertTrue(svc.listBookings(roomA).isEmpty());
    }

    @Test
    void cancelUnknownIdDoesNothing() {
        BookingService svc = newService();
        svc.book(roomA, alice, at(600, 660));

        svc.cancelBooking("no-such-id");

        assertEquals(1, svc.listBookings(roomA).size());
    }
}
