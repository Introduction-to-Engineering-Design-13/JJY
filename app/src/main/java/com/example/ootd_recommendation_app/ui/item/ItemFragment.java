package com.example.ootd_recommendation_app.ui.item;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ootd_recommendation_app.MyItemRecyclerViewAdapter;
import com.example.ootd_recommendation_app.R;
import com.example.ootd_recommendation_app.placeholder.PlaceholderContent;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class ItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<ChecklistItem> items = new ArrayList<>(); // items 초기화

        ChecklistItem topItem = new ChecklistItem("상의", false, 0);
        List<ChecklistItem> topSubItems = new ArrayList<>();
        topSubItems.add(new ChecklistItem("후드", false, 1));
        topSubItems.add(new ChecklistItem("맨투맨", false, 1));
        topSubItems.add(new ChecklistItem("셔츠", false, 1));
        topSubItems.add(new ChecklistItem("티셔츠", false, 1));
        topSubItems.add(new ChecklistItem("니트/스웨터", false, 1));
        for (ChecklistItem sub : topSubItems) {
            sub.parent = topItem;
        }
        topItem.subItems = topSubItems;
        items.add(topItem);


        ChecklistItem bottomItem = new ChecklistItem("하의", false, 0);
        List<ChecklistItem> bottomSubItems = new ArrayList<>();
        bottomSubItems.add(new ChecklistItem("데님 팬츠", false, 1));
        bottomSubItems.add(new ChecklistItem("트레이닝/조거", false, 1));
        bottomSubItems.add(new ChecklistItem("코튼", false, 1));
        bottomSubItems.add(new ChecklistItem("슬렉스", false, 1));
        bottomSubItems.add(new ChecklistItem("반바지", false, 1));
        for (ChecklistItem sub : bottomSubItems) {
            sub.parent = bottomItem;
        }
        bottomItem.subItems = bottomSubItems;
        items.add(bottomItem);

        ChecklistItem ShoesItem = new ChecklistItem("신발", false, 0);
        List<ChecklistItem> ShoesSubItems = new ArrayList<>();
        ShoesSubItems.add(new ChecklistItem("스니커즈", false, 1));
        ShoesSubItems.add(new ChecklistItem("부츠/워커", false, 1));
        ShoesSubItems.add(new ChecklistItem("구두", false, 1));
        ShoesSubItems.add(new ChecklistItem("스포츠화", false, 1));
        ShoesSubItems.add(new ChecklistItem("샌들", false, 1));
        for (ChecklistItem sub : ShoesSubItems) {
            sub.parent = ShoesItem;
        }
        ShoesItem.subItems = ShoesSubItems;
        items.add(ShoesItem);

        ChecklistItem OuterItem = new ChecklistItem("아우터", false, 0);
        List<ChecklistItem> OuterSubItems = new ArrayList<>();
        OuterSubItems.add(new ChecklistItem("후드집업", false, 1));
        OuterSubItems.add(new ChecklistItem("가죽 자켓", false, 1));
        OuterSubItems.add(new ChecklistItem("카디건", false, 1));
        OuterSubItems.add(new ChecklistItem("청자켓", false, 1));
        OuterSubItems.add(new ChecklistItem("블레이저", false, 1));
        OuterSubItems.add(new ChecklistItem("스타디움 자켓", false, 1));
        OuterSubItems.add(new ChecklistItem("바람막이", false, 1));
        OuterSubItems.add(new ChecklistItem("숏패딩", false, 1));
        OuterSubItems.add(new ChecklistItem("코트", false, 1));
        OuterSubItems.add(new ChecklistItem("무스탕", false, 1));
        OuterSubItems.add(new ChecklistItem("롱패딩", false, 1));
        OuterSubItems.add(new ChecklistItem("트레이닝 자켓", false, 1));
        for (ChecklistItem sub : OuterSubItems) {
            sub.parent = OuterItem;
        }
        OuterItem.subItems = OuterSubItems;
        items.add(OuterItem);

        ChecklistAdapter adapter = new ChecklistAdapter(items);
        recyclerView.setAdapter(adapter);// 체크 결과 전달


        Button saveButton = view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(v -> {
            sendCheckedItemsToHome(adapter.getAllItems()); // adapter에서 최신 상태 받아와야 함
            requireActivity().onBackPressed(); // 저장 후 뒤로가기
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