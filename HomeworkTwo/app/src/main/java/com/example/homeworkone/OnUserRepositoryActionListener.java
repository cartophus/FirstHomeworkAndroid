package com.example.homeworkone;

import java.util.List;

// You can use an interface like this to perform actions on main thread
// when the action is done.
public interface OnUserRepositoryActionListener {
    void actionSuccess();
    void actionSuccess(List<User> result);
    void actionFailed();
}