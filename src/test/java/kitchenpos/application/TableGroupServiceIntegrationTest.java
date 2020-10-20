package kitchenpos.application;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kitchenpos.dao.OrderTableDao;
import kitchenpos.domain.OrderTable;
import kitchenpos.domain.TableGroup;

class TableGroupServiceIntegrationTest extends ServiceIntegrationTest {
    @Autowired
    TableGroupService tableGroupService;

    @Autowired
    OrderTableDao orderTableDao;

    @DisplayName("새로운 단체 손님을 지정한다.")
    @Test
    void create() {
        Long[] ids = {3L, 4L};
        TableGroup tableGroup = new TableGroup();
        tableGroup.setOrderTables(getOrderTablesWithId(ids));

        TableGroup persist = tableGroupService.create(tableGroup);

        assertThat(persist.getOrderTables()).extracting(OrderTable::getId).containsExactlyInAnyOrder(ids);
    }

    @DisplayName("단체 손님을 해제한다.")
    @Test
    void ungroup() {
        Long[] ids = {3L, 4L};
        TableGroup tableGroup = new TableGroup();
        tableGroup.setOrderTables(getOrderTablesWithId(ids));
        TableGroup persist = tableGroupService.create(tableGroup);
        Long id = persist.getId();

        tableGroupService.ungroup(id);

        assertThat(orderTableDao.findAllByTableGroupId(id)).isEmpty();
    }
}