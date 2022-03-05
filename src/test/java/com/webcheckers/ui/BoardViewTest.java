package com.webcheckers.ui;

import com.webcheckers.model.*;
import com.webcheckers.ui.BoardView;
import com.webcheckers.ui.Row;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Tag("Model-tier")
public class BoardViewTest {
    @Test
    public void test_Constructor(){
        BoardView board = new BoardView();
        assertNotNull(board);
    }


}