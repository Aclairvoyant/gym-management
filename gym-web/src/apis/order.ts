
import request from "@/utils/request";

interface OrderList {
    pageNum: number,
    pageSize: number,
    courseName?: string
}


export const getOrderListService = ({pageNum, pageSize}: OrderList) => {
    return request.get('/order/list', {
        params: {
            pageNum,
            pageSize
        }
    });
};

export const searchOrderService = ({pageNum, pageSize, courseName}: OrderList) => {
    console.log(pageNum, pageSize, courseName)
    return request.get('/order/search', {
        params: {
            pageNum,
            pageSize,
            courseName
        }
    });
};
