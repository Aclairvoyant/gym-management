import request from '@/utils/request';
import {AxiosPromise} from "axios";

interface CoachList {
    pageNum: number,
    pageSize: number,
    coachRealName?: string
}

interface CoachAddParams {
    coachRealName: string,
    coachSex: number,
    coachPhone: string,
    coachRemark: string
}

interface CoachEditParams {
    coachId: string,
    coachRealName: string,
    coachSex: number,
    coachPhone: string,
    coachRemark: string
}

interface CoachDeleteParams {
    coachId: string
}


export const getCoachListService = ({ pageNum, pageSize }: CoachList) => {
    console.log(pageNum, pageSize)
    return request.get('/coach/list', {
        params: {
            pageNum,
            pageSize
        }
    });
};


export const addCoachService = ({coachRealName, coachSex, coachPhone, coachRemark}: CoachAddParams): AxiosPromise => {
    return request.post('/coach/add', {coachRealName, coachSex, coachPhone, coachRemark});
}

export const editCoachService = ({coachId, coachRealName, coachSex, coachPhone, coachRemark}: CoachEditParams): AxiosPromise => {
    return request.put('/coach/edit', {coachId, coachRealName, coachSex, coachPhone, coachRemark});
}

export const deleteCoachService = ({coachId}: CoachDeleteParams): AxiosPromise => {
    console.log(coachId)
    return request.delete('/coach/delete', {
        params: {
            coachId
        }
    });
}

export const searchCoachService = ({pageNum, pageSize, coachRealName}: CoachList) => {
    console.log(pageNum, pageSize, coachRealName)
    return request.get('/coach/search', {
        params: {
            pageNum,
            pageSize,
            coachRealName
        }
    });
};

