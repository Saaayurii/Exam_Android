# Экзаменационный билет №9
## Теоретический вопрос

**Дисциплина:** Программирование мобильных устройств  
**Студент:** Долженко Роман Павлович  
**Специальность:** 09.03.01  
**Группа:** ИВТ-1  
**Университет:** ДонГУ, Физико-технический факультет  

---

## Вопрос: Объясните работу адаптера в RecyclerView и ViewPager

---

## Ответ

### 1. Понятие адаптера в Android

**Адаптер (Adapter)** — это архитектурный паттерн «Адаптер», реализованный в Android для связи источника данных с визуальным компонентом (виджетом). Адаптер выступает посредником между **моделью данных** (список объектов, массив, база данных) и **представлением** (RecyclerView, ViewPager), которое эти данные отображает.

Адаптер отвечает за три основные задачи:
1. Сообщить виджету, **сколько** элементов нужно отобразить.
2. **Создать** визуальный контейнер (ViewHolder / Fragment) для каждого элемента.
3. **Заполнить** контейнер данными для конкретной позиции.

---

### 2. Адаптер в RecyclerView

#### 2.1 Класс RecyclerView.Adapter

Для работы RecyclerView необходим адаптер, унаследованный от `RecyclerView.Adapter<VH>`, где `VH` — тип ViewHolder.

**Обязательные методы для переопределения:**

```java
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<String> dataList;

    public MyAdapter(List<String> dataList) {
        this.dataList = dataList;
    }

    // 1. Создаёт новый ViewHolder (inflate макета элемента списка)
    // Вызывается только когда нет переиспользуемых ViewHolder-ов
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    // 2. Привязывает данные к существующему ViewHolder (заполняет UI)
    // Вызывается каждый раз, когда элемент становится видимым
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(dataList.get(position));
    }

    // 3. Сообщает количество элементов
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    // ViewHolder — хранит ссылки на View элемента списка
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvItem);
        }
    }
}
```

#### 2.2 Паттерн ViewHolder

**ViewHolder** — внутренний класс, хранящий ссылки на элементы UI одного элемента списка. Это ключевая оптимизация RecyclerView:

- Без ViewHolder: `findViewById()` вызывался бы при каждой прокрутке (медленный обход дерева View)
- С ViewHolder: ссылки кэшируются при создании, при повторном использовании — данные просто переписываются

#### 2.3 Механизм переиспользования (Recycling)

RecyclerView не создаёт View для каждого элемента данных — он поддерживает **пул (RecycledViewPool)** невидимых ViewHolder-ов:

```
Данные:  [0] [1] [2] [3] [4] [5] [6] [7] ...
Экран:         [1] [2] [3] [4] [5]
                 ↓
RecyclerView держит в памяти только видимые + несколько скрытых.
При прокрутке: ViewHolder элемента [1] уходит в пул → заполняется данными [6] → появляется снизу.
```

Это позволяет отображать **миллионы элементов** при минимальном потреблении памяти.

#### 2.4 Методы уведомления об изменениях

```java
adapter.notifyDataSetChanged();          // Перерисовать всё (неэффективно)
adapter.notifyItemInserted(position);    // Добавлен элемент
adapter.notifyItemRemoved(position);     // Удалён элемент
adapter.notifyItemChanged(position);     // Изменён элемент
```

Для сложных изменений используется **DiffUtil** — утилита, вычисляющая минимальный набор изменений между старым и новым списком.

---

### 3. Адаптер в ViewPager2

#### 3.1 FragmentStateAdapter

ViewPager2 работает с адаптером типа `FragmentStateAdapter`, унаследованным от `RecyclerView.Adapter`. Это не случайно: ViewPager2 внутри использует RecyclerView как механизм прокрутки.

**Обязательные методы:**

```java
public class TabsPagerAdapter extends FragmentStateAdapter {

    private final int pageCount;

    public TabsPagerAdapter(FragmentActivity activity, int pageCount) {
        super(activity);
        this.pageCount = pageCount;
    }

    // Создаёт Fragment для нужной позиции
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new HomeFragment();
            case 1: return new ProfileFragment();
            case 2: return new AboutFragment();
            default: return new HomeFragment();
        }
    }

    // Количество страниц
    @Override
    public int getItemCount() {
        return pageCount;
    }
}
```

#### 3.2 Управление жизненным циклом фрагментов

`FragmentStateAdapter` автоматически управляет жизненным циклом фрагментов:

| Состояние | Что происходит |
|-----------|----------------|
| Фрагмент становится видимым | `onCreateView()` → `onStart()` → `onResume()` |
| Фрагмент уходит за пределы `offscreenPageLimit` | `onPause()` → `onStop()` → `onDestroyView()` (состояние сохраняется) |
| Фрагмент возвращается в область видимости | `onCreateView()` (состояние восстанавливается из Bundle) |

Это отличает `FragmentStateAdapter` от устаревшего `FragmentPagerAdapter` (ViewPager1), который **не уничтожал** фрагменты — что приводило к высокому потреблению памяти.

#### 3.3 Связка TabLayout + ViewPager2

```java
// Связка через TabLayoutMediator
new TabLayoutMediator(tabLayout, viewPager,
        (tab, position) -> tab.setText(tabTitles[position])
).attach();
```

**TabLayoutMediator** — класс-медиатор, который:
- Синхронизирует выбранную вкладку TabLayout с текущей страницей ViewPager2
- Автоматически обновляет UI при смене страницы (жест свайп или клик по вкладке)
- Устраняет необходимость вручную слушать `OnPageChangeCallback`

---

### 4. Сравнение адаптеров RecyclerView и ViewPager2

| Критерий | RecyclerView.Adapter | FragmentStateAdapter |
|----------|---------------------|----------------------|
| Базовый класс | `RecyclerView.Adapter<VH>` | `RecyclerView.Adapter<VH>` |
| Единица содержимого | View (ViewHolder) | Fragment |
| Переиспользование | Активное (пул ViewHolder) | Ограниченное (offscreenPageLimit) |
| Управление состоянием | Ручное (DiffUtil, notify*) | Автоматическое (FragmentManager) |
| Типичное применение | Большие списки данных | Страницы/вкладки (обычно 3–10) |

---

### 5. Вывод

Адаптер в Android реализует паттерн «Посредник» между данными и UI-компонентом. В **RecyclerView** адаптер вместе с паттерном ViewHolder обеспечивает эффективное переиспользование View при больших наборах данных. В **ViewPager2** адаптер (`FragmentStateAdapter`) управляет фрагментами как страницами и интегрируется с `FragmentManager` для корректного управления жизненным циклом. Несмотря на разное назначение, оба адаптера основаны на одном механизме `RecyclerView`, что обеспечивает унификацию и предсказуемое поведение.
