package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static java.lang.Thread.sleep;

import java.awt.event.FocusEvent.Cause;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.deathnote.impl.DeathNoteImplementation;

class TestDeathNote {
    private static final String NAME1 = "Fede";
    private static final String NAME2 = "Gigi";
    private static final String DEATH_CAUSE1 = "heart attack";
    private static final String DEATH_CAUSE2 = "karting accident";
    private static final String DEATH_DETAILS1 = "ran for too long";
    private static final String DEATH_DETAILS2 = "ran too slow";

    private DeathNoteImplementation dn;

    @BeforeEach
    void setUp() {
        dn = new DeathNoteImplementation();
    }

    @Test
    void testRuleNumber() {
        try {
            dn.getRule(0);
            dn.getRule(-1);
        } catch (Exception e) {
            testException(e, IllegalArgumentException.class);
        }
    }

    @Test
    void testNoNullRules() {
        for (String e : dn.RULES) {
            assertFalse(e.isEmpty());
            assertFalse(e.isBlank());
            assertNotNull(e);
        }
    }

    @Test
    void testHumanName() {
        assertFalse(dn.isNameWritten(NAME1));
        dn.writeName(NAME1);
        assertTrue(dn.isNameWritten(NAME1));
        assertFalse(dn.isNameWritten(NAME2));
        assertFalse(dn.isNameWritten(""));
    }

    @Test
    void testDeathCause() {
        try {
            dn.writeDeathCause(DEATH_CAUSE1);
            dn.writeName(NAME1);
            dn.writeDeathCause(DEATH_CAUSE1);
            assertEquals(DEATH_CAUSE1, dn.getDeathCause(NAME1));
            dn.writeName(NAME2);
            assertTrue(dn.writeDeathCause(DEATH_CAUSE2));
            assertEquals(dn.getDeathCause(NAME2), DEATH_CAUSE2);
            sleep(100);
            dn.writeDeathCause(DEATH_CAUSE1);
            assertEquals(dn.getDeathCause(NAME2), DEATH_CAUSE2);
        } catch (Exception e) {
            testException(e, IllegalStateException.class);
        }
    }

    @Test
    void testDeathDetails() {
        try {
            dn.writeDetails(DEATH_DETAILS1);
            dn.writeName(NAME1);
            assertTrue(dn.getDeathDetails(NAME1).isEmpty());
            assertTrue(dn.writeDetails(DEATH_DETAILS1));
            assertEquals(DEATH_CAUSE1, dn.getDeathDetails(NAME1));
            dn.writeName(NAME2);
            sleep(6100);
            dn.writeDetails(DEATH_DETAILS2);
            assertNotEquals(DEATH_CAUSE2, dn.getDeathDetails(NAME2));
        } catch (Exception e) {
            testException(e, IllegalStateException.class);
        }
    } 

    private void testException(Exception e, Class<? extends Exception> c) {
        assertEquals(c, e.getClass());
        assertNotNull(e.getMessage());
        assertFalse(e.getMessage().isBlank());
        assertFalse(e.getMessage().isEmpty());
    }
}