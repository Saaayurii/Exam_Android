package com.example.ticket9.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.ticket9.fragment.HomeFragment;
import com.example.ticket9.fragment.ProfileFragment;
import com.example.ticket9.fragment.AboutFragment;

/**
 * Адаптер для ViewPager2 (Практика 2, Билет 9).
 *
 * FragmentStateAdapter — базовый класс для работы ViewPager2 с фрагментами.
 *
 * Принцип работы:
 *  - ViewPager2 запрашивает фрагмент для нужной позиции через createFragment()
 *  - getItemCount() сообщает общее количество страниц
 *  - FragmentStateAdapter автоматически управляет жизненным циклом фрагментов:
 *    сохраняет состояние при прокрутке и уничтожает невидимые фрагменты
 *    (за пределами offscreenPageLimit) для экономии памяти
 */
public class TabsPagerAdapter extends FragmentStateAdapter {

    private final int tabCount;

    public TabsPagerAdapter(@NonNull FragmentActivity fragmentActivity, int tabCount) {
        super(fragmentActivity);
        this.tabCount = tabCount;
    }

    /**
     * Создаёт фрагмент для указанной позиции.
     * Вызывается ViewPager2 при первом отображении страницы.
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:  return new HomeFragment();
            case 1:  return new ProfileFragment();
            case 2:  return new AboutFragment();
            default: return new HomeFragment();
        }
    }

    /**
     * Возвращает общее количество страниц/вкладок.
     */
    @Override
    public int getItemCount() {
        return tabCount;
    }
}
