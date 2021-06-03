package com.example.assignment_final.Activity;


import android.view.View;
import android.widget.FrameLayout;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import com.luseen.spacenavigation.SpaceNavigationView;

public class ScrollHandler extends CoordinatorLayout.Behavior<SpaceNavigationView> {
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, SpaceNavigationView child, View dependency) {
        return dependency instanceof FrameLayout;

    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, SpaceNavigationView child,
                                       View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, SpaceNavigationView child,
                                  View target, int dx, int dy, int[] consumed) {
        if (dy < 0) {
            showBottomNavigationView(child);
        } else if (dy > 0) {
            hideBottomNavigationView(child);
        }
    }

    private void hideBottomNavigationView(SpaceNavigationView view) {
        view.animate().translationY(view.getHeight());
    }

    private void showBottomNavigationView(SpaceNavigationView view) {
        view.animate().translationY(0);
    }
}
