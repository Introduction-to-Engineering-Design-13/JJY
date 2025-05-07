package com.example.ootd_recommendation_app.ui.item;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ootd_recommendation_app.MyItemRecyclerViewAdapter;
import com.example.ootd_recommendation_app.R;
import com.example.ootd_recommendation_app.data.ChecklistItemEntity;
import com.example.ootd_recommendation_app.placeholder.PlaceholderContent;
import com.example.ootd_recommendation_app.repository.ChecklistRepository;
import com.example.ootd_recommendation_app.ui.home.HomeViewModel;

import java.util.ArrayList;
import java.util.List;


public class ItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;


    public ItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ItemFragment newInstance(int columnCount) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                Log.d("SCROLL_TEST", "스크롤 dy: " + dy);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);
        recyclerView.setNestedScrollingEnabled(true);
        List<ChecklistItem> items = new ArrayList<>(); // items 초기화

        ChecklistItem topItem = new ChecklistItem("상의", false, 0);
        List<ChecklistItem> topSubItems = new ArrayList<>();

        ChecklistItem hoodie = new ChecklistItem("후드", false, 1);
        ChecklistItem mtm = new ChecklistItem("맨투맨", false, 1);
        ChecklistItem shirt = new ChecklistItem("셔츠", false, 1);
        ChecklistItem tshirt = new ChecklistItem("티셔츠", false, 1);
        ChecklistItem knit = new ChecklistItem("니트/스웨터", false, 1);

        topSubItems.add(hoodie);
        topSubItems.add(mtm);
        topSubItems.add(shirt);
        topSubItems.add(tshirt);
        topSubItems.add(knit);

// 상위 설정
        for (ChecklistItem sub : topSubItems) {
            sub.parent = topItem;

            // ✅ 하위 색상 추가
            List<ChecklistItem> colors = new ArrayList<>();
            colors.add(new ChecklistItem("Green", false, 2));
            colors.add(new ChecklistItem("Blue", false, 2));
            colors.add(new ChecklistItem("Yellow", false, 2));
            colors.add(new ChecklistItem("Pink", false, 2));
            colors.add(new ChecklistItem("Navy", false, 2));
            colors.add(new ChecklistItem("White", false, 2));
            colors.add(new ChecklistItem("Brown", false, 2));
            colors.add(new ChecklistItem("Black", false, 2));
            colors.add(new ChecklistItem("Red", false, 2));

            for (ChecklistItem color : colors) {
                color.parent = sub; // 색상의 부모는 "후드" 등
            }

            sub.subItems = colors;
        }

        topItem.subItems = topSubItems;
        items.add(topItem);

        ChecklistItem bottomItem = new ChecklistItem("하의", false, 0);
        List<ChecklistItem> bottomSubItems = new ArrayList<>();

        ChecklistItem denim = new ChecklistItem("데님 팬츠", false, 1);
        ChecklistItem joger = new ChecklistItem("트레이닝/조거", false, 1);
        ChecklistItem cotton = new ChecklistItem("코튼", false, 1);
        ChecklistItem slacks = new ChecklistItem("슬렉스", false, 1);
        ChecklistItem shorts = new ChecklistItem("반바지", false, 1);

        bottomSubItems.add(denim);
        bottomSubItems.add(joger);
        bottomSubItems.add(cotton);
        bottomSubItems.add(slacks);
        bottomSubItems.add(shorts);

// 상위 설정
        for (ChecklistItem sub : bottomSubItems) {
            sub.parent = bottomItem;

            // ✅ 하위 색상 추가
            List<ChecklistItem> colors = new ArrayList<>();
            colors.add(new ChecklistItem("Deep Blue", false, 2));
            colors.add(new ChecklistItem("Light Blue", false, 2));
            colors.add(new ChecklistItem("Beige", false, 2));
            colors.add(new ChecklistItem("Khaki", false, 2));
            colors.add(new ChecklistItem("Charcoal", false, 2));
            colors.add(new ChecklistItem("Black", false, 2));
            colors.add(new ChecklistItem("Brown", false, 2));

            for (ChecklistItem color : colors) {
                color.parent = sub;
            }

            sub.subItems = colors;
        }

        bottomItem.subItems = bottomSubItems;
        items.add(bottomItem);


        ChecklistItem ShoesItem = new ChecklistItem("신발", false, 0);
        List<ChecklistItem> ShoesSubItems = new ArrayList<>();

        ChecklistItem sneackers = new ChecklistItem("스니커즈", false, 1);
        ChecklistItem boots = new ChecklistItem("부츠/워커", false, 1);
        ChecklistItem dressshoes = new ChecklistItem("구두", false, 1);
        ChecklistItem sportshoes = new ChecklistItem("스포츠화", false, 1);
        ChecklistItem sandals = new ChecklistItem("샌들", false, 1);

        ShoesSubItems.add(sneackers);
        ShoesSubItems.add(boots);
        ShoesSubItems.add(dressshoes);
        ShoesSubItems.add(sportshoes);
        ShoesSubItems.add(sandals);

// 상위 설정
        for (ChecklistItem sub : ShoesSubItems) {
            sub.parent = ShoesItem;

            // ✅ 하위 색상 추가
            List<ChecklistItem> colors = new ArrayList<>();
            colors.add(new ChecklistItem("Gray", false, 2));
            colors.add(new ChecklistItem("Beige", false, 2));
            colors.add(new ChecklistItem("White", false, 2));
            colors.add(new ChecklistItem("Black", false, 2));

            for (ChecklistItem color : colors) {
                color.parent = sub; // 색상의 부모는 "후드" 등
            }

            sub.subItems = colors;
        }

        ShoesItem.subItems = ShoesSubItems;
        items.add(ShoesItem);

        ChecklistItem OuterItem = new ChecklistItem("아우터", false, 0);
        List<ChecklistItem> OuterSubItems = new ArrayList<>();

        ChecklistItem ziphoodie = new ChecklistItem("후드집업", false, 1);
        ChecklistItem latherjacket = new ChecklistItem("가죽 자켓", false, 1);
        ChecklistItem kardigan = new ChecklistItem("가디건", false, 1);
        ChecklistItem denimjacket = new ChecklistItem("청자켓", false, 1);
        ChecklistItem blazer = new ChecklistItem("블레이저", false, 1);
        ChecklistItem stadiumjacket = new ChecklistItem("스타이움 자켓", false, 1);
        ChecklistItem windbreaker = new ChecklistItem("바람막이", false, 1);
        ChecklistItem shortpuffer = new ChecklistItem("숏패딩", false, 1);
        ChecklistItem coat = new ChecklistItem("코트", false, 1);
        ChecklistItem Shearling  = new ChecklistItem("무스탕", false, 1);
        ChecklistItem longpuffer = new ChecklistItem("롱패딩", false, 1);
        ChecklistItem traingjacket = new ChecklistItem("트레이닝 자켓", false, 1);

        OuterSubItems.add(ziphoodie);
        OuterSubItems.add(latherjacket);
        OuterSubItems.add(kardigan);
        OuterSubItems.add(denimjacket);
        OuterSubItems.add(blazer);
        OuterSubItems.add(stadiumjacket);
        OuterSubItems.add(windbreaker);
        OuterSubItems.add(shortpuffer);
        OuterSubItems.add(coat);
        OuterSubItems.add(Shearling);
        OuterSubItems.add(longpuffer);
        OuterSubItems.add(traingjacket);

// 상위 설정
        for (ChecklistItem sub : OuterSubItems) {
            sub.parent = OuterItem;

            // ✅ 하위 색상 추가
            List<ChecklistItem> colors = new ArrayList<>();
            colors.add(new ChecklistItem("Gray", false, 2));
            colors.add(new ChecklistItem("Beige", false, 2));
            colors.add(new ChecklistItem("White", false, 2));
            colors.add(new ChecklistItem("Black", false, 2));
            colors.add(new ChecklistItem("Deep Blue", false, 2));
            colors.add(new ChecklistItem("Light Blue", false, 2));

            for (ChecklistItem color : colors) {
                color.parent = sub;
            }

            sub.subItems = colors;
        }

        OuterItem.subItems = OuterSubItems;
        items.add(OuterItem);


        ChecklistAdapter adapter = new ChecklistAdapter(items);
        recyclerView.setAdapter(adapter);// 체크 결과 전달


        Button saveButton = view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(v -> {
            List<ChecklistItem> allItems = adapter.getAllItems();
            List<ChecklistItem> checkedLeafItems = new ArrayList<>();
            for (ChecklistItem item : allItems) {
                collectCheckedLeafItems(item, checkedLeafItems);  // ← 최하위 항목 수집
            }

            ArrayList<String> paths = new ArrayList<>();
            for (ChecklistItem leaf : checkedLeafItems) {
                String path = leaf.getFullPath();
                paths.add(path);
            }

            // 🔹 HomeFragment로 전달
            Bundle result = new Bundle();
            result.putStringArrayList("checked_items", paths);
            getParentFragmentManager().setFragmentResult("checklist_result", result);

            // 🔹 ViewModel에 데이터 전달
            HomeViewModel viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
            viewModel.addCheckedItems(paths);  // ViewModel에서 DB 저장 처리
            requireActivity().onBackPressed();
        });

        return view;
    }

    private void sendCheckedItemsToHome(List<ChecklistItem> items) {
        List<ChecklistItem> checkedLeafItems = new ArrayList<>();
        for (ChecklistItem item : items) {
            // 이 아이템이 체크되었고 하위 항목이 없거나 비어있으면 추가
            if (item.isChecked() && (item.subItems == null || item.subItems.isEmpty())) {
                checkedLeafItems.add(item);
            }

            // 하위 항목이 있으면 체크된 하위 항목도 확인
            if (item.subItems != null && !item.subItems.isEmpty()) {
                for (ChecklistItem subItem : item.subItems) {
                    if (subItem.isChecked()) {
                        checkedLeafItems.add(subItem);
                    }
                }
            }
        }

        // HomeFragment로 전달
        ArrayList<String> paths = new ArrayList<>();
        for (ChecklistItem leaf : checkedLeafItems) {
            String path = leaf.getFullPath();
            paths.add(path);
        }

        Bundle result = new Bundle();
        result.putStringArrayList("checked_items", paths);
        getParentFragmentManager().setFragmentResult("checklist_result", result);
    }

    private void collectCheckedLeafItems(ChecklistItem item, List<ChecklistItem> result) {
        if (item.isChecked()) {
            if (item.subItems == null || item.subItems.isEmpty()) {
                result.add(item);
            } else {
                // 하위 항목이 있으면 각 하위 항목 처리
                for (ChecklistItem subItem : item.subItems) {
                    collectCheckedLeafItems(subItem, result);
                }
            }
        }
    }
}