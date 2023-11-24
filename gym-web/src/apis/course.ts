import request from '@/utils/request';
import {AxiosPromise} from "axios";

interface CourseList {
    pageNum: number,
    pageSize: number,
    courseName?: string
}

// interface CoachList {
//     coachRealName: string,
//     coachId?: string
// }

interface CourseAddParams {
    courseName: string,
    coachRealName: string,
    courseFee: number,
    scheduleStart: Date,
    scheduleEnd: Date,
}

interface CourseEditParams {
    courseId: string,
    courseName: string,
    coachId: string,
    coachRealName: string,
    courseFee: number,
    scheduleStart: Date,
    scheduleEnd: Date,
}

interface CourseDeleteParams {
    courseId: string
}


export const getCourseListService = ({ pageNum, pageSize }: CourseList) => {
    console.log(pageNum, pageSize)
    return request.get('/course/list', {
        params: {
            pageNum,
            pageSize
        }
    });
};


export const addCourseService = ({courseName, coachRealName,  courseFee, scheduleStart, scheduleEnd}: CourseAddParams): AxiosPromise => {
    return request.post('/course/add', {courseName, coachRealName,  courseFee, scheduleStart, scheduleEnd});
}

export const editCourseService = ({courseId, courseName, coachRealName, coachId, courseFee, scheduleStart, scheduleEnd}: CourseEditParams): AxiosPromise => {
    return request.put('/course/edit', {courseId, courseName, coachRealName, coachId, courseFee, scheduleStart, scheduleEnd});
}

export const deleteCourseService = ({courseId}: CourseDeleteParams): AxiosPromise => {
    return request.delete('/course/delete', {
        params: {
            courseId
        }
    });
}

export const searchCourseService = ({pageNum, pageSize, courseName}: CourseList) => {
    console.log(pageNum, pageSize, courseName)
    return request.get('/course/search', {
        params: {
            pageNum,
            pageSize,
            courseName
        }
    });
};

export const getCoachListService = () => {
    return request.get('/course/getCoachList')
}
