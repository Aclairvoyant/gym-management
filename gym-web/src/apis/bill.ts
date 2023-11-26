
import request from "@/utils/request";

interface BillList {
    pageNum: number,
    pageSize: number,
}


export const getBillListService = ({pageNum, pageSize}: BillList) => {
    return request.get('/member/bill/list', {
        params: {
            pageNum,
            pageSize
        }
    });
};
