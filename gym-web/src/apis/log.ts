
import request from "@/utils/request";

interface LogList {
    pageNum: number,
    pageSize: number,
    query?: string
}


export const getLogListService = ({pageNum, pageSize}: LogList) => {
    return request.get('/log/list', {
        params: {
            pageNum,
            pageSize
        }
    });
};

export const searchLogService = ({pageNum, pageSize, query}: LogList) => {
    console.log(pageNum, pageSize, query)
    return request.get('/log/search', {
        params: {
            pageNum,
            pageSize,
            query
        }
    });
};
