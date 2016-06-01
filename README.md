# BottomCalendar
a demo of calendar created by myself
we can use it like this:
    BottomDateDialog.Builder builder = new BottomDateDialog.Builder(MainActivity.this, selectedDate);
                final BottomDialog dialog = builder.create();
                dialog.show();
                builder.setOnDialogClick(new BottomDateDialog.Builder.OnDialogClick() {
                    @Override
                    public void onClick(String date) {
                        selectedDate = date;
                    }
                });
