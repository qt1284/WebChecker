package com.webcheckers.ui;

import com.webcheckers.model.Space;
import com.webcheckers.ui.Row;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class RowTest {

    final Space sOne = mock(Space.class);
    final Space sTwo = mock(Space.class);
    final Space[] spaces = {sOne, sTwo};

    final Row row = new Row(spaces, 2);

    @Test
    public void testRowNotNull(){
        assertNotNull(row);
    }

    @Test
    public void testGetIndex(){
        assertEquals(2, row.getIndex());
    }

    @Test
    public void testGetIndexValid(){
        int flag = 0;
        if(row.getIndex() <= 7 && row.getIndex() >= 0)
            flag = 1;
        assertEquals(1, flag);
    }

    @Test
    public void testGetSpaces(){
        Space[] test = row.getSpaces();
        assertEquals(sOne, test[0]);
        assertEquals(sTwo, test[1]);
    }
    @Test
    public void equalTest(){
        Row row = new Row(spaces, 2);
        assertEquals(this.row,row);
    }
    @Test
    public void testIterator(){
        assertNotNull(row.iterator());
    }

}